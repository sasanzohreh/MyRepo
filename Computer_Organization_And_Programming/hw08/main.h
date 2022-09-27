#ifndef MAIN_H
#define MAIN_H

#include "gba.h"

// TODO: Create any necessary structs

typedef struct {u16 tileimg[8192];} charblock;
#define CHARBLOCKBASE ((charblock*)0x6000000)

typedef struct{
	u16 attr0;
	u16 attr1;
	u16 attr2;
	u16 fill;
} OamEntry;
#define OAMMEM ((OamEntry*)0x7000000)

struct sprite {
	int xcoor;
	int ycoor;
	int width;
	int height;
	OamEntry* ref;
	int isCaught;
};

/*
* For example, for a Snake game, one could be:
*
* struct snake {
*   int heading;
*   int length;
*   int row;
*   int col;
* };
*
* Example of a struct to hold state machine data:
*
* struct state {
*   int currentState;
*   int nextState;
* };
*
*/

#endif
