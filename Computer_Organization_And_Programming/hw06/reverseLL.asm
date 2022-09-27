;;=============================================================
;; CS 2110 - Spring 2020
;; Homework 6 - Reverse Linked List
;;=============================================================
;; Name: Sasan Zohreh
;;============================================================

;; In this file, you must implement the 'reverseLL' subroutine.

;; Little reminder from your friendly neighborhood 2110 TA staff: don't run
;; this directly by pressing 'RUN' in complx, since there is nothing put at
;; address x3000. Instead, load it and use 'Debug' -> 'Simulate
;; Subroutine Call' and choose the 'reverseLL' label.

.orig x3000
HALT

;; Pseudocode (see PDF for explanation):
;;
;; Arguments of reverseLL: Node head
;;
;; reverseLL(Node head) {
;;     // note that a NULL address is the same thing as the value 0
;;     if (head == NULL) {
;;         return NULL;
;;     }
;;     if (head.next == NULL) {
;;         return head;
;;     }
;;     Node tail = head.next;
;;     Node newHead = reverseLL(tail);
;;     tail.next = head;
;;     head.next = NULL;
;;     return newHead;
;; }
reverseLL

;; YOUR CODE HERE
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

;;Body of Subroutine
LDR R0,R5,#4	;R0 stores HEAD
BRnp IFEL1	;if HEAD==NULL, return 0
AND R1,R1,#0
BR RETURN

IFEL1 NOP
LDR R1,R0,#0	;R1 stores HEAD.next
BRnp IFEL2	;if HEAD.next==null, return HEAD
ADD R1,R0,#0	;R1 stores HEAD
BR RETURN

IFEL2 NOP
LDR R1,R0,#0	;R1 stores tail
ADD R6,R6,#-1	;push tail
STR R1,R6,#0
JSR reverseLL	;recurse
LDR R2,R6,#0	;pop return value - store in R2=newHead
ADD R6,R6,#1
ADD R3,R1,#0	;R3 stores tail.next
LDR R3,R3,#0
ADD R3,R0,#0	;tail.next = head
ADD R4,R0,#8	;R4 stores head.next
AND R3,R3,#0	;R3=0
STR R3,R4,#0	;head.next=NULL
ADD R1,R2,#0	;R1 stores newHead

RETURN
STR R1,R5,#3

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

;; The following is an example of a small linked list that starts at x4000.
;;
;; The first number (offset 0) contains the address of the next node in the
;; linked list, or zero if this is the final node.
;;
;; The second number (offset 1) contains the data of this node.
.orig x4000
.fill x4008
.fill 5
.end

.orig x4008
.fill x4010
.fill 12
.end

.orig x4010
.fill 0
.fill -7
.end
