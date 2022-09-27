;;=============================================================
;; CS 2110 - Spring 2021
;; Homework 5 - Iterative GCD
;;=============================================================
;; Name: Sasan Zohreh
;;=============================================================

;; Pseudocode (see PDF for explanation):
;;
;; a = (argument 1);
;; b = (argument 2);
;; ANSWER = 0;
;;
;; while (a != b) {
;;   if (a > b) {
;;     a = a - b;
;;   } else {
;;     b = b - a;
;;   }
;; }
;; ANSWER = a;

.orig x3000

;; put your code here

        LD R1,A         ;puts value of a in R1
        LD R2,B         ;puts value of b in R2
        AND R3,R3,#0    ;sets R3 to 0 which represents ANSWER
W1      NOT R4,R2       ;sets R4 to -b
        ADD R4,R4,#1
        ADD R0,R1,R4    ;check if a - b = 0, if it does stop loop
        BRz ENDW1
        AND R0,R0,#0    ;clear R0 and check if a > b, if it is go to else (EL1)
        ADD R0,R1,R4
        BRnz EL1
        ADD R1,R1,R4    ;a = a - b
        BR ELF1
EL1     NOP
        NOT R5,R1       ;sets R5 to -a
        ADD R5,R5,#1
        ADD R2,R2,R5    ;b = b - a
ELF1    NOP
BR W1
ENDW1   NOP
ST R1,ANSWER
HALT

A .fill 20
B .fill 19

ANSWER .blkw 1

.end
