;;=============================================================
;; CS 2110 - Spring 2021
;; Homework 6 - Bubble Sort with Compare
;;=============================================================
;; Name: Sasan Zohreh
;;=============================================================

;; In this file, you must implement the 'SORT' subroutine.

;; Little reminder from your friendly neighborhood 2110 TA staff: don't run
;; this directly by pressing 'RUN' in complx, since there is nothing put at
;; address x3000. Instead, load it and use 'Debug' -> 'Simulate
;; Subroutine Call' and choose the 'reverseLL' label.

.orig x3000
HALT

;; Pseudocode (see PDF for explanation):
;;
;; array: memory address of the first element in the array
;; len: integer value of the number of elements in the array
;; compare: memory address of the subroutine used to compare elements
;;
;; sort(array, len, function compare) {
;;     // last index of the array
;;     y = len - 1;
;;     while(y > 0) {
;;         x = 0;
;;         while(x < y) {
;;             // if compare returns 1, swap
;;             if (compare(ARRAY[x], ARRAY[x+1]) > 0) {
;;                 temp = ARRAY[x];
;;                 ARRAY[x] = ARRAY[x+1];
;;                 ARRAY[x+1] = temp;
;;             }
;;             x++;
;;         }
;;         y--;
;;     }
;; }
;;
;; HINT: compare will be passed as a parameter on the stack. It will be a
;; a pointer to one of the subroutines below. Think about which instruction
;; allows you to call a subroutine with a memory address that is stored in a register
SORT

;; PUT YOUR CODE HERE
ADD R6,R6,#-4	;Allocate space rv,ra,fp,lv1
STR R7,R6,#2	;Save Ret Addr
STR R5,R6,#1	;Save Old FP
ADD R5,R6,#0	;Copy SP to FP
ADD R6,R6,#-5	;Make room for saved regs
STR R0,R5,#-1	;Save R0
STR R1,R5,#-2	;Save R1
STR R2,R5,#-3	;Save R2
STR R3,R5,#-4	;Save R3
STR R4,R5,#-5	;Save R4

;;BODY OF SUBROUTINE
LDR R0,R5,#4			;R0 stores array
LDR R1,R5,#5			;R1 stores len
ADD R1,R1,#-1			;R1 stores y=len-1
W1	ADD R1,R1,#0		;if y > 0, loop
BRnz ENDW1
	AND R2,R2,#0		;R2 stores x
	W2	ADD R3,R2,#0	;R3 stores -x
		NOT R3,R3
		ADD R3,R3,#1
		ADD R3,R3,R1
	BRnz ENDW2		;if y-x>0, loop
		LDR R0,R5,#4	;R0 stores array
		ADD R3,R0,R2	;R3 stores ARRAY[x]
		LDR R3,R3,#0	
		ADD R0,R0,R2	;R0 stores ARRAY[x+1]
		ADD R0,R0,#1
		LDR R0,R0,#0	
		ADD R6,R6,#-1	;push ARRAY[x+1]
		STR R0,R6,#0
		ADD R6,R6,#-1	;push ARRAY[x]
		STR R3,R6,#0
		LDR R4,R5,#6	;R4 stores function compare
		JSRR R4		;jump to compare
		ADD R4,R3,#0	;R4 is temp = ARRAY[x]
		LDR R3,R6,#0
		ADD R6,R6,#2	;pop compare and arg 1-2
		ADD R3,R3,#0
		BRnz ENDIF
		
		LDR R0,R5,#4	;R0 stores array
		ADD R3,R0,R2	;R3 stores ARRAY address at ARRAY + x
		ADD R0,R0,R2	;R0 stores ARRAY[x+1]
		ADD R0,R0,#1
		LDR R0,R0,#0
		STR R0,R3,#0	;ARRAY[x] = ARRAY[x+1]
		ADD R3,R3,#1	;R3 stores ARRAY address at ARRAY + x + 1
		STR R4,R3,#0	;ARRAY[x+1] = temp
		ENDIF NOP
		ADD R2,R2,#1	;x++
	BR W2
	ENDW2 NOP
ADD R1,R1,#-1			;y--
BR W1
ENDW1 NOP

STR R0,R5,#3	;return
LDR R4,R5,#-5	;Restore R4
LDR R3,R5,#-4	;Restore R3
LDR R2,R5,#-3	;Restore R2
LDR R1,R5,#-2	;Restore R1
LDR R0,R5,#-1	;Restore R0
ADD R6,R5,#0	;Restore SP
LDR R5,R6,#1	;Restore FP
LDR R7,R6,#2	;Restore RA
ADD R6,R6,#3	;Pop ra,fp,lv1
RET

;; used by the autograder
STACK .fill xF000
.end

;; USE FOR DEBUGGING IN COMPLEX
;; load array at x4000 and put the length as 7
;; you can use the memory addresses of the subroutines below for the last parameter

;; ARRAY
.orig x4000
    .fill 4
    .fill -9
    .fill 0
    .fill -2
    .fill 9
    .fill 3
    .fill -10
.end

;; The following subroutines are possible functions that may be passed
;; as the function address parameter into the sorting function.
;; DO NOT edit the code below; it will be used by the autograder.
.orig x5000
;; returns a positive number if a>b
;; compare(a,b) for ascending sort
CMPGT
    .fill   x1DBD
    .fill   x7180
    .fill   x7381
    .fill   x6183
    .fill   x6384
    .fill   x927F
    .fill   x1261
    .fill   x1201
    .fill   x0C03
    .fill   x5020
    .fill   x1021
    .fill   x0E01
    .fill   x5020
    .fill   x7182
    .fill   x6180
    .fill   x6381
    .fill   x1DA2
    .fill   xC1C0
.end

.orig x5100
;; returns a positive number if b>a
;; compare(a,b) for descending sort
CMPLT
    .fill   x1DBD
    .fill   x7180
    .fill   x7381
    .fill   x6183
    .fill   x6384
    .fill   x927F
    .fill   x1261
    .fill   x1201
    .fill   x0603
    .fill   x5020
    .fill   x1021
    .fill   x0E01
    .fill   x5020
    .fill   x7182
    .fill   x6180
    .fill   x6381
    .fill   x1DA2
    .fill   xC1C0
.end

.orig x5200
;; returns a positive number if |a| > |b|
;; compare(a,b) for ascending sort on magnitudes (absolute value)
CMPABS
    .fill   x1DBD
    .fill   x7180
    .fill   x7381
    .fill   x6183
    .fill   x0602
    .fill   x903F
    .fill   x1021
    .fill   x6384
    .fill   x0C02
    .fill   x927F
    .fill   x1261
    .fill   x1240
    .fill   x0C03
    .fill   x5020
    .fill   x1021
    .fill   x0E01
    .fill   x5020
    .fill   x7182
    .fill   x6180
    .fill   x6381
    .fill   x1DA2
    .fill   xC1C0
.end
