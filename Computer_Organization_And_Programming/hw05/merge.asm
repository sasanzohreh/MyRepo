;;=============================================================
;; CS 2110 - Spring 2020
;; Homework 5 - Array Merge
;;=============================================================
;; Name: Sasan Zohreh
;;=============================================================

;; Pseudocode (see PDF for explanation):
;;
;; x = 0;
;; y = 0;
;; z = 0;
;; while (x < LENGTH_X && y < LENGTH_Y) {
;;   if (ARR_X[x] <= ARR_Y[y]) {
;;     ARR_RES[z] = ARR_X[x];
;;     z++;
;;     x++;
;;   } else {
;;     ARR_RES[z] = ARR_Y[y];
;;     z++;
;;     y++;
;;   }
;; }
;; while(x < ARRX.length) {
;;   ARR_RES[z] = ARR_X[x];
;;   z++;
;;   x++;
;; }
;; while (y < ARRY.length) {
;;   ARR_RES[z] = ARR_Y[y];
;;   z++;
;;   y++;
;; }



.orig x3000

;; put your code here
    AND R0,R0,#0    ;R0 stores x
    AND R1,R1,#0    ;R1 stores y
    AND R2,R2,#0    ;R2 stores z
    
W1  NOT R3, R0      ;R3 stores -x
    ADD R3,R3,#1
    LD R7, LENGTH_X 
    ADD R3,R3,R7    ;if LENGTH_X - x is > 0 keep looping
BRnz ENDW1
    NOT R4,R1       ;R4 stores -y
    ADD R4,R4,#1
    LD R7, LENGTH_Y 
    ADD R4,R4,R7    ;if LENGTH_Y - y is > 0 keep looping
BRnz ENDW1
    LD R5,ARR_X    ;R5 stores ARR_X[x]
    ADD R5,R5,R0
    LDR R5,R5,#0
    LD R6,ARR_Y    ;R6 stores ARR_Y[y]
    ADD R6,R6,R1
    LDR R6,R6,#0
    NOT R5,R5       ;negate R5 holding ARR_X[x]
    ADD R5,R5,#1
    ADD R5,R5,R6
BRn EL1
    LD R3,ARR_X     ;R3 holds ARR_X[x]
    ADD R3,R3,R0
    LDR R3,R3,#0
    LD R6,ARR_RES   ;R6 holds address of ARR_RES + z
    ADD R6,R6,R2
    STR R3,R6,#0    ;ARR_RES[z] = ARR_X[x]
    LDR R4,R6,#0
    ADD R0,R0,#1    ;x++
    ADD R2,R2,#1    ;z++
BR ELF1
EL1 NOP
    LD R3,ARR_Y     ;R3 holds ARR_Y[y]
    ADD R3,R3,R1
    LDR R3,R3,#0
    LD R6,ARR_RES   ;R6 holds address of ARR_RES + z
    ADD R6,R6,R2
    STR R3,R6,#0    ;ARR_RES[z] = ARR_Y[y]
    LDR R4,R6,#0
    ADD R1,R1,#1    ;y++
    ADD R2,R2,#1    ;z++
BR ELF1
ELF1 NOP
BR W1
ENDW1 NOP

W2  NOT R3, R0      ;R3 stores -x
    ADD R3,R3,#1
    LD R7, LENGTH_X 
    ADD R3,R3,R7    ;if LENGTH_X - x is > 0 keep looping
BRnz ENDW2
    LD R3,ARR_X     ;R3 holds ARR_X[x]
    ADD R3,R3,R0
    LDR R3,R3,#0
    LD R6,ARR_RES   ;R6 holds address of ARR_RES + z
    ADD R6,R6,R2
    STR R3,R6,#0    ;ARR_RES[z] = ARR_X[x]
    LDR R4,R6,#0
    ADD R0,R0,#1    ;x++
    ADD R2,R2,#1    ;z++
BR W2
ENDW2 NOP

W3  NOT R3, R1      ;R3 stores -y
    ADD R3,R3,#1
    LD R7, LENGTH_Y 
    ADD R3,R3,R7    ;if LENGTH_Y - y is > 0 keep looping
BRnz ENDW3
    LD R3,ARR_Y     ;R3 holds ARR_Y[y]
    ADD R3,R3,R1
    LDR R3,R3,#0
    LD R6,ARR_RES   ;R6 holds address of ARR_RES + z
    ADD R6,R6,R2
    STR R3,R6,#0    ;ARR_RES[z] = ARR_Y[y]
    LDR R4,R6,#0
    ADD R1,R1,#1    ;y++
    ADD R2,R2,#1    ;z++
BR W3
ENDW3 NOP

HALT

ARR_X      .fill x4000
ARR_Y      .fill x4100
ARR_RES    .fill x4200

LENGTH_X   .fill 5
LENGTH_Y   .fill 7
LENGTH_RES .fill 12

.end

.orig x4000
.fill 1
.fill 5
.fill 10
.fill 11
.fill 12
.end

.orig x4100
.fill 3
.fill 4
.fill 6
.fill 9
.fill 15
.fill 16
.fill 17
.end
