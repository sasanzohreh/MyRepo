/**
 * @file hw7.c
 * @author Sasan Zohreh
 * @brief structs, pointers, pointer arithmetic, arrays, strings, and macros
 * @date 2021-03-22
 */

// DO NOT MODIFY THE INCLUDE(S) LIST
#include <stdio.h>
#include "hw7.h"
#include "my_string.h"

// Global array of student structs
struct student class[MAX_CLASS_SIZE];

int size = 0;

/** addStudent
 *
 * @brief creates a new student struct and adds it to the array of student structs, "class"
 *
 *
 * @param "name" name of the student being created and added
 *               NOTE: if the length of name (including the null terminating character)
 *               is above MAX_NAME_SIZE, truncate name to MAX_NAME_SIZE
 * @param "age" age of the student being created and added
 * @param "gpa" gpa of the student being created and added
 * @param "id" id of the student being created and added
 * @return FAILURE on failure, SUCCESS on success
 *         Failure if any of the following are true:
 *         (1) the length of "id" is less than MIN_ID_SIZE
 *         (2) a student with the name "name" already exits in the array "class"
 *         (3) adding the new student would cause the size of the array "class" to
 *             exceed MAX_CLASS_SIZE
 */
int addStudent(const char *name, int age, double gpa, const char *id)
{
  char newName[MAX_NAME_SIZE];
  //if length of id is < MIN_ID_SIZE or adding a new element causes size to be > MAX_CLASS_SIZE, return 0
  if((my_strlen(id) < MIN_ID_SIZE) || (size + 1 > MAX_CLASS_SIZE))
		return FAILURE;
  //copy name into char[], cutting it off after MAX_NAME_SIZE characters + null-terminating character
  if (my_strlen(name) + 1 > MAX_NAME_SIZE) {
		my_strncpy(newName,name,MAX_NAME_SIZE-1);
		newName[MAX_NAME_SIZE-1] = '\0';
  } else {
		my_strncpy(newName,name,my_strlen(name)+1);
		newName[my_strlen(name)] = '\0';
  }
  //if name already exists in class, return 0
  for (int i = 0; i < size; i++) {
		if (my_strncmp(newName,class[i].name,my_strlen(name)) == 0)
			return FAILURE;
  }
  //create new struct, fill in variables, store in class
  struct student stud;
  my_strncpy(stud.name, newName, sizeof newName/sizeof newName[0]);
  stud.age = age;
  stud.gpa = gpa;
  my_strncpy(stud.id, id, my_strlen(id)+1);
  class[size] = stud;
  size++;
  return SUCCESS;
}

/** updateStudentName
 *
 * @brief updates the name of an existing student in the array of student structs, "class"
 *
 * @param "s" student struct that exists in the array "class"
 * @param "name" new name of student "s"
 *               NOTE: if the length of name (including the null terminating character)
 *               is above MAX_NAME_SIZE, truncate name to MAX_NAME_SIZE
 * @return FAILURE on failure, SUCCESS on success
 *         Failure if any of the following are true:
 *         (1) the student struct "s" can not be found in the array "class"
 */
int updateStudentName(struct student s, const char *name)
{
  //search through whole array
  for(int i = 0; i < size; i++) {
	    //if name matches, truncate new name if necessary, then store new name in current student
		if ((my_strncmp(s.name,class[i].name,my_strlen(s.name)) == 0)){
			char newName[MAX_NAME_SIZE];
			if (my_strlen(name) + 1 > MAX_NAME_SIZE) {
				my_strncpy(newName,name,MAX_NAME_SIZE-1);
				newName[MAX_NAME_SIZE-1] = '\0';
			} else {
				my_strncpy(newName,name,my_strlen(name)+1);
				newName[my_strlen(name)] = '\0';
			}
			my_strncpy(class[i].name, newName, sizeof newName/sizeof newName[0]);
			return SUCCESS;
		}
  }
  return FAILURE;
}

/** swapStudents
 *
 * @brief swaps the position of two student structs in the array of student structs, "class"
 *
 * @param "index1" index of the first student struct in the array "class"
 * @param "index2" index of the second student struct in the array "class"
 * @return FAILURE on failure, SUCCESS on success
 *         Failure if any of the following are true:
 *         (1) "index1" and/or "index2" are negative numbers
 *         (2) "index1" and/or "index2" are out of bounds of the array "class"
 */
int swapStudents(int index1, int index2)
{
	//if either index is negative or > size-1, return 0;
	if(index1 < 0 || index2 < 0 || index1 > size-1 || index2 > size-1){
		return FAILURE;
	}
	//copy class[index1] to temp var
	struct student temp;
	my_strncpy(temp.name,class[index1].name,my_strlen(class[index1].name)+1);
	temp.age = class[index1].age;
	temp.gpa = class[index1].gpa;
	my_strncpy(temp.id,class[index1].id,my_strlen(class[index1].id)+1);
	//copy class[index2] to class[index1]
	my_strncpy(class[index1].name,class[index2].name,my_strlen(class[index2].name)+1);
	class[index1].age = class[index2].age;
	class[index1].gpa = class[index2].gpa;
	my_strncpy(class[index1].id,class[index2].id,my_strlen(class[index2].id)+1);
	//copy temp to class[index2]
	my_strncpy(class[index2].name,temp.name,my_strlen(temp.name)+1);
	class[index2].age = temp.age;
	class[index2].gpa = temp.gpa;
	my_strncpy(class[index2].id,temp.id,my_strlen(temp.id)+1);
	return SUCCESS;
}

/** removeStudent
 *
 * @brief removes an existing student in the array of student structs, "class"
 *
 * @param "s" student struct that exists in the array "class"
 * @return FAILURE on failure, SUCCESS on success
 *         Failure if any of the following are true:
 *         (1) the student struct "s" can not be found in the array "class"
 */
int removeStudent(struct student s)
{
	int index = 0;
	//search through whole array until match is found, if it is, break out of loop
	for(index = 0; index < size; index++) {
		if ((my_strncmp(s.name,class[index].name,my_strlen(s.name)) == 0)){
			break;
		}
	}
	//if no match was found, return 0
	if(index == size)
		return FAILURE;
	//fill in spot of removed element by moving each element to the right, to the left
	if(size > 1){
		for(int i = index; i < size-1; i++)
			swapStudents(i, i+1);
	}
	//if size is 0, don't make it negative
	if(size > 0)
		size--;
	return SUCCESS;
}

/** compareStudentID
 *
 * @brief using ASCII, compares the last three characters (not including the NULL
 * terminating character) of two students' IDs
 *
 * @param "s1" student struct that exists in the array "class"
 * @param "s2" student struct that exists in the array "class"
 * @return negative number if s1 is less than s2, positive number if s1 is greater
 *         than s2, and 0 if s1 is equal to s2
 */
int compareStudentID(struct student s1, struct student s2)
{
	//compare last 3 digits until a mismatch is found, otherwise loop ends
	for(int i = 3; i >= 0; i--) {
		if(*(s1.id + my_strlen(s1.id) - i) >  *(s2.id + my_strlen(s2.id) - i)){
			return 1;
		}
		if(*(s1.id + my_strlen(s1.id) - i) <  *(s2.id + my_strlen(s2.id) - i)){
			return -1;
		}
	}
  return 0;
}

/** sortStudents
 *
 * @brief using the compareStudentID function, sort the students in the array of
 * student structs, "class," by the last three characters of their student IDs
 *
 * @param void
 * @return void
 */
void sortStudents(void)
{
	//perform bubble sort to sort students by their id in ascending order
	for (int i = 0; i < size; i++) {
		for (int j = 0; j < size-1; j++) {
			if(compareStudentID(class[j], class[j+1]) > 0){
				swapStudents(j, j+1);
			}
		}
	}
}
