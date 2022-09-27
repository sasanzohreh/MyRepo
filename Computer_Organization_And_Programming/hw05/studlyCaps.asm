;;=============================================================
;; CS 2110 - Fall 2021
;; Homework 5 - Studly Caps!
;;=============================================================
;; Name: Sasan Zohreh
;;=============================================================

;; Pseudocode (see PDF for explanation)
;;
;; string = "TWENTY 1 ten";
;; i = 0;
;; while (string[i] != 0) {
;;   if (i % 2 == 0) {
;;   // should be lowercase
;;     if ('A' <= string[i] <= 'Z') {
;;       string[i] = string[i] | 32;
;;     }
;;   } else {
;;   // should be uppercase
;;     if ('a' <= string[i] <= 'z') {
;;       string[i] = string[i] & ~32;
;;     }
;;   }
;;   i++;
;; }

.orig x3000

;; put your code here
AND R0,R0,#0    ;R0 represents i

W1  LD R1, STRING   ;R1 represents ith character in string
    ADD R1,R1,R0
    LDR R1,R1,#0    ;if string[i] == 0, stop looping
BRz ENDW1
    AND R2,R0,#1    ;if i % 2 != 0, do else (EL1)
BRnp EL1
        LD R2,UPPERA    ;R2 stores -UPPERA
        AND R3,R3,#0    ;R3 stores string[i]
        ADD R3,R3,R1
        NOT R2,R2
        ADD R2,R2,#1
        ADD R3,R3,R2    ;if string[i] - UPPERA < 0, move to end of loop
    BRn ELF1
        LD R2,UPPERZ    ;R2 stores not UPPERZ
        AND R3,R3,#0    ;R3 stores -string[i]
        ADD R3,R3,R1
        NOT R3,R3
        ADD R3,R3,#1
        ADD R3,R3,R2    ;if UPPERZ - string[i] < 0, move to end of loop
    BRn ELF1
        AND R2,R2,#0    ;R2 holds string[i]
        ADD R2,R2,R1
        AND R3,R3,#0    ;R3 holds 32
        ADD R3,R3,#15   
        ADD R3,R3,#15
        ADD R3,R3,#2
        NOT R2,R2       ;OR operation
        AND R3,R3,R2
        NOT R2,R2
        ADD R3,R3,R2    ;R3 now holds string[i] | 32;
        LD R2,STRING
        ADD R2,R2,R0
        STR R3,R2,#0    ;string[i] = string[i] | 32;
    BR ELF1
EL1 NOP
        LD R2,LOWERA    ;R2 stores -LOWERA
        AND R3,R3,#0    ;R3 stores string[i]
        ADD R3,R3,R1
        NOT R2,R2
        ADD R2,R2,#1
        ADD R3,R3,R2    ;if string[i] - LOWERA < 0, move to end of loop
    BRn ELF1
        LD R2,LOWERZ    ;R2 stores not LOWERZ
        AND R3,R3,#0    ;R3 stores -string[i]
        ADD R3,R3,R1
        NOT R3,R3
        ADD R3,R3,#1
        ADD R3,R3,R2    ;if LOWERZ - string[i] < 0, move to end of loop
    BRn ELF1
        AND R2,R2,#0    ;R2 holds string[i]
        ADD R2,R2,R1
        AND R3,R3,#0    ;R3 holds ~32
        ADD R3,R3,#15   
        ADD R3,R3,#15
        ADD R3,R3,#2
        NOT R3,R3
        AND R3,R3,R2    ;R3 holds string[i] & ~32
        LD R2,STRING
        ADD R2,R2,R0
        STR R3,R2,#0    ;string[i] = string[i] & ~32;
ELF1 NOP
ADD R0,R0,#1
BR W1
ENDW1 NOP

HALT

UPPERA .fill x41    ;; A in ASCII
UPPERZ .fill x5A	;; Z in ASCII
LOWERA .fill x61	;; a in ASCII
LOWERZ .fill x7A	;; z in ASCII

STRING .fill x4000
.end

.orig x4000
.stringz "TWENTY ONE TEN"
.end
