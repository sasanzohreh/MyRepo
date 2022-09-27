/*
 * CS 2110 Homework 10 Spring 2021
 * Author: Sasan Zohreh
 */

/* we need this for uintptr_t */
#include <stdint.h>
/* we need this for memcpy/memset */
#include <string.h>
/* we need this to print out stuff*/
#include <stdio.h>
/* we need this for the metadata_t struct and my_malloc_err enum definitions */
#include "my_malloc.h"

/* Function Headers
 * Here is a place to put all of your function headers
 * Remember to declare them as static
 */

/* Our freelist structure - our freelist is represented as a singly linked list
 * the freelist is sorted by address;
 */
metadata_t *address_list;

/* Set on every invocation of my_malloc()/my_free()/my_realloc()/
 * my_calloc() to indicate success or the type of failure. See
 * the definition of the my_malloc_err enum in my_malloc.h for details.
 * Similar to errno(3).
 */
enum my_malloc_err my_malloc_errno;



// -------------------- PART 1: Helper functions --------------------

/* The following prototypes represent useful helper functions that you may want
 * to use when writing your malloc functions. You do not have to implement them
 * first, but we recommend reading over their documentation and prototypes;
 * having a good idea of the kinds of helpers you can use will make it easier to
 * plan your code.
 *
 * None of these functions will be graded individually. However, implementing
 * and using these will make programming easier. We may provide ungraded test
 * cases for some of these functions after the assignment is released.
 */


/* OPTIONAL HELPER FUNCTION: find_right
 * Given a pointer to a free block, this function searches the freelist for another block to the right of the provided block.
 * If there is a free block that is directly next to the provided block on its right side,
 * then return a pointer to the start of the right-side block.
 * Otherwise, return null.
 * This function may be useful when implementing my_free().
 */
static metadata_t *find_right(metadata_t *freed_block) {
	if (freed_block->next == NULL)
		return NULL;
	return freed_block->next;
}

/* OPTIONAL HELPER FUNCTION: find_left
 * This function is the same as find_right, but for the other side of the newly freed block.
 * This function will be useful for my_free(), but it is also useful for my_malloc(), since whenever you sbrk a new block,
 * you need to merge it with the block at the back of the freelist if the blocks are next to each other in memory.
 */

static metadata_t *find_left(metadata_t *freed_block){
	if ((uintptr_t)freed_block == (uintptr_t)address_list)
		return NULL;
	metadata_t *curr = address_list;
	while (curr->next != NULL) {
		if (curr->next == freed_block)
			break;
		curr = curr->next;
	}
	return curr;
}

/* OPTIONAL HELPER FUNCTION: merge
 * This function should take two pointers to blocks and merge them together.
 * The most important step is to increase the total size of the left block to include the size of the right block.
 * You should also copy the right block's next pointer to the left block's next pointer. If both blocks are initially in the freelist, this will remove the right block from the list.
 * This function will be useful for both my_malloc() (when you have to merge sbrk'd blocks) and my_free().
 */
static void merge(metadata_t *left, metadata_t *right) {
	left->size += right->size + TOTAL_METADATA_SIZE;
	left->next = right->next;
}

/* OPTIONAL HELPER FUNCTION: split_block
 * This function should take a pointer to a large block and a requested size, split the block in two, and return a pointer to the new block (the right part of the split).
 * Remember that you must make the right side have the user-requested size when splitting. The left side of the split should have the remaining data.
 * We recommend doing the following steps:
 * 1. Compute the total amount of memory that the new block will take up (both metadata and user data).
 * 2. Using the new block's total size with the address and size of the old block, compute the address of the start of the new block.
 * 3. Shrink the size of the old/left block to account for the lost size. This block should stay in the freelist.
 * 4. Set the size of the new/right block and return it. This block should not go in the freelist.
 * This function will be useful for my_malloc(), particularly when the best-fit block is big enough to be split.
 */
static metadata_t *split_block(metadata_t *block, size_t size) {
	metadata_t *split = (metadata_t*)((uint8_t*)block + (block->size - size));
	block->size -= size + TOTAL_METADATA_SIZE;
	split->size = size;
	return split;
}

/* OPTIONAL HELPER FUNCTION: add_to_addr_list
 * This function should add a block to freelist.
 * Remember that the freelist must be sorted by address. You can compare the addresses of blocks by comparing the metadata_t pointers like numbers (do not dereference them).
 * Don't forget about the case where the freelist is empty. Remember what you learned from Homework 9.
 * This function will be useful for my_malloc() (mainly for adding in sbrk blocks) and my_free().
 */
static void add_to_addr_list(metadata_t *block) {
	if (address_list == NULL) 
		address_list = block;
	else {
		metadata_t *curr = address_list;
		//if the pointer to block is lower than the pointer to address_list, add block to the front of the list
		if ((uintptr_t)address_list > (uintptr_t)block){
			block->next = address_list;
			address_list = block;
			return;
		}
		//loop until we find the first next pointer that's larger than block
		while (curr->next != NULL && (uintptr_t)curr->next < (uintptr_t)block)
			curr = curr->next;
		if (curr->next != NULL)
			block->next = curr->next;
		curr->next = block;
	}
}

/* OPTIONAL HELPER FUNCTION: remove_from_addr_list
 * This function should remove a block from the freelist.
 * Simply search through the freelist, looking for a node whose address matches the provided block's address.
 * This function will be useful for my_malloc(), particularly when the best-fit block is not big enough to be split.
 */
static void remove_from_addr_list(metadata_t *block) {
	metadata_t *left = find_left(block);
	if (left == NULL)
		address_list = address_list->next;
	else
		left->next = block->next;
}

/* OPTIONAL HELPER FUNCTION: find_best_fit
 * This function should find and return a pointer to the best-fit block. See the PDF for the best-fit criteria.
 * Remember that if you find the perfectly sized block, you should return it immediately.
 * You should not return an imperfectly sized block until you have searched the entire list for a potential perfect block.
 */
static metadata_t *find_best_fit(size_t size) {
	metadata_t* curr = address_list;
	metadata_t* min = address_list;
	int mins = SBRK_SIZE * 2;
	while (curr != NULL) {
		if (curr->size == size)
			return curr;
		//keep track of the block with the smallest size that's still larger than the size we want
		if (curr->size > size && (int)curr->size < mins){
			min = curr;
			mins = min->size;
		}
		curr = curr->next;
	}
	return min;
}




// ------------------------- PART 2: Malloc functions -------------------------

/* Before starting each of these functions, you should:
 * 1. Understand what the function should do, what it should return, and what the freelist should look like after it finishes
 * 2. Develop a high-level plan for how to implement it; maybe sketch out pseudocode
 * 3. Check if the parameters have any special cases that need to be handled (when they're NULL, 0, etc.)
 * 4. Consider what edge cases the implementation needs to handle
 * 5. Think about any helper functions above that might be useful, and implement them if you haven't already
 */


/* MALLOC
 * See PDF for documentation
 */
void *my_malloc(size_t size) {
    my_malloc_errno = NO_ERROR;
	if (size > SBRK_SIZE - TOTAL_METADATA_SIZE){
		my_malloc_errno = SINGLE_REQUEST_TOO_LARGE;
		return NULL;
	}
	if (size == 0)
		return NULL;
	metadata_t* best = find_best_fit(size);
	//if we have no blocks or no blocks big enough, get more with my_sbrk
	if(best == NULL || best->size < size) {
		best = my_sbrk(SBRK_SIZE);
		//if we have no memory left, return NULL
		if (best == (void*)-1) {
			my_malloc_errno = OUT_OF_MEMORY;
			return NULL;
		}
		best->size = SBRK_SIZE - TOTAL_METADATA_SIZE;
		best->next = NULL;
		add_to_addr_list(best);
		//if the pointer to the node to the left of the block we added is right next to it in memory, merge them together
		if(find_left(best) != NULL && (uintptr_t)((uint8_t*)find_left(best) + find_left(best)->size + TOTAL_METADATA_SIZE ) == (uintptr_t)best)
			merge(find_left(best), best);
		//now that we created a block big enough, find the best block again
		best = find_best_fit(size);
	}
	//if the block is the perfect fit or too small to split, return the whole block's space
	if (best->size == size || best->size - size < MIN_BLOCK_SIZE) {
		remove_from_addr_list(best);
		return ++best;
	} else {
		//split the block and return a pointer to the space available
		metadata_t* temp = split_block(best, size);
		return ++temp;
	}
	
    // Reminder of how to do malloc:
    // 1. Make sure the size is not too small or too big.
    // 2. Search for a best-fit block. See the PDF for information about what to check.
    // 3. If a block was not found:
    // 3.a. Call sbrk to get a new block.
    // 3.b. If sbrk fails (which means it returns -1), return NULL.
    // 3.c. If sbrk succeeds, add the new block to the freelist. If the new block is next to another block in the freelist, merge them.
    // 3.d. Go to step 2.
    // 4. If the block is too small to be split (see PDF for info regarding this), then remove the block from the freelist and return a pointer to the block's user section.
    // 5. If the block is big enough to split:
    // 5.a. Split the block into a left side and a right side. The right side should be the perfect size for the user's requested data.
    // 5.b. Keep the left side in the freelist.
    // 5.c. Return a pointer to the user section of the right side block.
}

/* REALLOC
 * See PDF for documentation
 */
void *my_realloc(void *ptr, size_t size) {
    if (ptr == NULL)
		return my_malloc(size);
	my_malloc_errno = NO_ERROR;
	if (size == 0)
		my_free(ptr);
	metadata_t* curr = my_malloc(size);
	if (curr == NULL)
		return NULL;
	//copy data from ptr to curr
	memcpy(curr , ptr, ((metadata_t*)(ptr)-1)->size);
    my_free(ptr);
    return curr;
	
	// Reminder of how to do realloc:
    // 1. If ptr is NULL, then only call my_malloc(size). If the size is 0, then only call my_free(ptr).
    // 2. Call my_malloc to allocate the requested number of bytes. If this fails, immediately return NULL and do not free the old allocation.
    // 3. Copy the data from the old allocation to the new allocation. We recommend using memcpy to do this. Be careful not to read or write out-of-bounds!
    // 4. Free the old allocation and return the new allocation.
}

/* CALLOC
 * See PDF for documentation
 */
void *my_calloc(size_t nmemb, size_t size) {
	metadata_t* curr = my_malloc(size * nmemb);
	if (curr == NULL)
		return NULL;
    my_malloc_errno = NO_ERROR;
	//set all the memory we allocated as 0
    memset((uint8_t*)curr, 0, nmemb * size);
	return curr;
	
    // Reminder for how to do calloc:
    // 1. Use my_malloc to allocate the appropriate amount of size.
    // 2. Clear all of the bytes that were allocated. We recommend using memset to do this.
}

/* FREE
 * See PDF for documentation
 */
void my_free(void *ptr) {
	my_malloc_errno = NO_ERROR;
	if (ptr == NULL)
		return;
    metadata_t* temp = (metadata_t*)ptr - 1;
	add_to_addr_list(temp);
	//if the pointer to the block to the left of temp is right next to it in memory, merge them together
	if (temp->next != NULL && (uintptr_t)((uint8_t*)temp + temp->size + TOTAL_METADATA_SIZE) == (uintptr_t)temp->next)
		merge(temp, temp->next);
	//if the pointer to the block to the right of temp is right next to it in memory, merge them together
	if (find_left(temp) != NULL && (uintptr_t)((uint8_t*)find_left(temp) + find_left(temp)->size + TOTAL_METADATA_SIZE) == (uintptr_t)temp)
		merge(find_left(temp), temp);

    // Reminder for how to do free:
    // 1. Since ptr points to the start of the user block, obtain a pointer to the metadata for the freed block.
    // 2. Look for blocks in the freelist that are positioned immediately before or after the freed block.
    // 2.a. If a block is found before or after the freed block, then merge the blocks.
    // 3. Once the freed block has been merged (if needed), add the freed block back to the freelist.
    // 4. Alternatively, you can do step 3 before step 2. Add the freed block back to the freelist,
    // then search through the freelist for consecutive blocks that need to be merged.

    // A lot of these steps can be simplified by implementing helper functions. We highly recommend doing this!

}
