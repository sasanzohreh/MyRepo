;;=============================================================
;; CS 2110 - Fall 2020
;; Homework 5 - Palindrome
;;=============================================================
;; Name: Sasan Zohreh
;;=============================================================

;; Pseudocode (see PDF for explanation):
;;
;; string = "racecar";
;; len = 0;
;;
;; // to find the length of the string
;; while (string[len] != '\0') {
;;   len = len + 1;
;; }
;;
;; // to check whether the string is a palindrome
;; result = 1;
;; i = 0;
;; while (i < length) {
;;   if (string[i] != string[length - i - 1]) {
;;     result = 0;
;;     break;
;;   }
;;   i = i + 1;
;; }

.orig x3000

;; put your code here

AND R0,R0,#0        ;R0 holds len
W1  LD R1, STRING   ;R1 represents lenth character in string
    ADD R1,R1,R0
    LDR R1,R1,#0
BRz ENDW1
    ADD R0,R0,#1
BR W1
ENDW1 NOP

AND R1,R1,#0        ;R1 holds result
ADD R1,R1,#1
AND R2,R2,#0        ;R2 holds i
W2  NOT R3,R2       ;R3 holds -i
    ADD R3,R3,#1
    ADD R4,R3,R0    ;if length - i <= 0, exit loop
BRnz ENDW2
    LD R4, STRING   ;R4 holds string[i]
    ADD R4,R4,R2
    LDR R4,R4,#0
    LD R5, STRING   ;R5 holds STRING + length
    ADD R5,R5,R0
    ADD R5,R5,R3    ;R5 holds STRING + length-i
    ADD R5,R5,#-1   ;R5 holds STRING + length-i-1
    LDR R5,R5,#0    ;R5 holds string[length-i-1]
    NOT R5,R5       ;R5 holds -string[length-i-1]
    ADD R5,R5,#1
    ADD R5,R5,R4    ;if string[i] - string[length-i-1] == 0, exit loop
BRz EL1
    AND R1,R1,#0
    BR ENDW2
EL1 NOP
ADD R2,R2,#1
BR W2
ENDW2 NOP

ST R1, ANSWER
HALT

ANSWER .blkw 1
STRING .fill x4000
.end

.orig x4000
.stringz "racecar"
.end
