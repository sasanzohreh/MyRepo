; This lab assignment serves as an introduction to many of the most
; used instructions in the LC-3.
; We recommend going to the LC-3 Resources folder in Canvas's files and
; looking at the documents there: they explain the function of every
; operation.

; The following line starts a program block. Do not delete.
.orig x3000


; First, load the value that is located at the memory location that is
; marked by the label INPUT. There are several load operations, so
; choose one with the right addressing mode.
; Put the value in register 0.

	LD R0, INPUT ; REPLACE WITH YOUR INSTRUCTION (R0 <- mem[INPUT])


; The value stored at label INPUT turns out to be a memory address.
; Load the value at the memory address that you just put in R0.
; You'll need a different load instruction for this.
; Store the value in register 1.

	LDR R1, R0, #0 ; REPLACE WITH YOUR INSTRUCTION (R1 <- mem[R0])


; There is another label, ARRAY, that marks the location of element zero
; of an array. Write two statements of assembly code to obtain the value
; at index 4 in the array.
; You will need two instructions: one to get the address of the label,
; and the other to read the data at the address's location, plus 4.
; Use only register 2 for this step.

	LEA R2, ARRAY ; REPLACE WITH YOUR INSTRUCTIONS (R2 <- mem[ARRAY + 4])
	LDR R2, R2, #4

; IMPORTANT NOTE: for other homeworks/labs, you may instead be given a label
; that contains an address of the starting element of the array, like so:
; ARRAY .fill x4000
; In this case, the array starts at x4000, so you will need to get that
; value in a register using LD before you can access the array.


; There is one more load instruction, LDI, called "load indirect". You
; can use LDI to do a double-load: this essentially combines the first
; two instructions that you wrote earlier. Try using LDI to double-load
; INPUT label. You will get the same value as you did previously.
; Put this value in register 3.

	LDI R3, INPUT ; REPLACE WITH YOUR INSTRUCTION (R3 <- mem[mem[INPUT]])
	

; Now for some arithmetic instructions.
; First, sum the values in register 1 and register 2, and store the
; result in register 4. Check the ISA for the correct operator ordering.

	ADD R4, R1, R2 ; REPLACE WITH YOUR INSTRUCTION (R4 <- R1 + R2)
	

; Next, negate the value in register 3. You'll notice that there isn't 
; a negation operator, or even a subtraction operator. However, you can
; use the negation equation that you learned earlier in the course:
; -X = ~X + 1
; There is an operation to easily calculate ~X.
; To add 1, use the imm5 version of ADD. See the ISA for how
; to properly format this.
; Do both of these operations in-place: put the result in register 3, and
; do not use any other registers to hold temporary values.

	NOT R3, R3 ; REPLACE WITH YOUR INSTRUCTIONS (R3 <- (-R3))
	ADD R3, R3, #1

; Sometimes, you want to copy a value from one register to another.
; Doing this is easy: just use the add-immediate instruction with 0.
; Copy the value currently in R4 to R5.

	ADD R5, R4, #0 ; REPLACE WITH YOUR INSTRUCTION (R5 <- R4)


; Last, put the value 32 in register 6. You'll first need to clear a
; register, which can be done by ANDing the register's value with 0.
; Then, just use imm5 adding to get to 32.
; Unfortunately, because the immediate value is limited to 5 bits,
; you can only add numbers between -16 and 15. You may need more
; instructions.

	AND R6, R6, #0 ; REPLACE WITH YOUR INSTRUCTIONS (R6 <- 32)
	ADD R6, R6, #15
	ADD R6, R6, #15
	ADD R6, R6, #2

; Now for store instructions. Many of these instructions are similar
; to the load instructions, so only the PC-relative store will be
; used for now. Make sure you also understand the STR instruction.
; Store the value in R4 into the memory location labeled OUTPUT.

	ST R4, OUTPUT ; REPLACE WITH YOUR INSTRUCTION (mem[OUTPUT] <- R4)
	

; Finally, the branch instruction. There are 8 different forms of this
; operation: BR, BRn, BRz, BRp, BRnz, BRnp, BRzp, and BRnzp.
; The letters after BR indicate which condition code values will cause
; BR to jump somewhere. Note that BR and BRnzp are identical.
; The following block of code is designed to check the sign of the
; number stored in R3 and either store -1, 0, or 1 at the memory
; location SIGN, depending on R3's value.

; You will first need to set the condition codes accordingly. To set
; the condition codes to determine the sign of R3, simply perform an
; equation that produces the value of R3, like R3 <- R3 + 0.

	ADD R3, R3, #0 ; REPLACE WITH YOUR INSTRUCTION (R3 <- R3 + 0)
	

; Next, write three BR statements that will move the program
; counter to one of three labels: NEG, ZER, or POS.
; For example, branch to NEGATIVE if the 'n' condition code is on.

	BRn NEG ; REPLACE WITH YOUR INSTRUCTIONS (three branching instructions)
	BRz ZER
	BRp POS

; Finally, add two lines of code at the marked spots to properly jump
; back to the END label. You should use an UNCONDITIONAL branch: this
; means it always jumps, and does not care what the condition codes are.

NEG		AND R7, R7, 0
		ADD R7, R7, -1
		ST R7, SIGN
		BR END ; PUT UNCONDITIONAL BRANCH TO END HERE
ZER		AND R7, R7, 0
		ST R7, SIGN
		BR END ; PUT UNCONDITIONAL BRANCH TO END HERE
POS		AND R7, R7, 0
		ADD R7, R7, 1
		ST R7, SIGN
END		NOP		; this is just an instruction that does nothing



; Now for the most important statement: stopping the program.
; Stopping the program uses a TRAP instruction.
; There are two ways to write this instruction, although one way makes
; the intent of the instruction much clearer.
; Hint: it rhymes with salt.

	HALT
	

; That's the end of the program! Run test.sh in your docker container
; to make sure your output is correct.

; This statement marks the end of a data block. Do not delete.
.end

; This marks the start of another block of data.
.orig x3040
; The .fill statement reserves a word of data and puts a value there.
; Note that sometimes, these values will be replaced by the autograder.
; The following input value and array will have its values replaced by
; the autograder; do not assume that these values are constants.
INPUT 	.fill x4000
ARRAY 	.fill #123
		.fill x234
		.fill #345
		.fill x456
		.fill #457
; The .blkw statement reserves a block of data. This line reserves a
; block for the output of some of the earlier operations.
OUTPUT	.blkw 1
SIGN	.blkw 1
.end

; This marks the start of another block of data.
.orig x4000
	.fill #100
.end
		