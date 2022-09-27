/*
 * Exported with nin10kit v1.8
 * Invocation command was nin10kit --mode=sprites --bpp=8 --for_bitmap --transparent=FF0000 graphics charizard.png player3.png venusaur.png blastoise.png 
 * Time-stamp: Friday 04/09/2021, 04:29:46
 * 
 * Image Information
 * -----------------
 * charizard.png 64@64
 * player3.png 16@16
 * venusaur.png 64@64
 * blastoise.png 64@64
 * Transparent color: (255, 0, 0)
 * 
 * All bug reports / feature requests are to be filed here https://github.com/TricksterGuy/nin10kit/issues
 */

#ifndef GRAPHICS_H
#define GRAPHICS_H

#define GRAPHICS_PALETTE_TYPE (1 << 13)
#define GRAPHICS_DIMENSION_TYPE (1 << 6)

extern const unsigned short graphics_palette[256];
#define GRAPHICS_PALETTE_SIZE 512
#define GRAPHICS_PALETTE_LENGTH 256

extern const unsigned short graphics[6272];
#define GRAPHICS_SIZE 12544
#define GRAPHICS_LENGTH 6272

#define CHARIZARD_PALETTE_ID (0 << 12)
#define CHARIZARD_SPRITE_SHAPE (0 << 14)
#define CHARIZARD_SPRITE_SIZE (3 << 14)
#define CHARIZARD_ID 512

#define PLAYER3_PALETTE_ID (0 << 12)
#define PLAYER3_SPRITE_SHAPE (0 << 14)
#define PLAYER3_SPRITE_SIZE (1 << 14)
#define PLAYER3_ID 640

#define VENUSAUR_PALETTE_ID (0 << 12)
#define VENUSAUR_SPRITE_SHAPE (0 << 14)
#define VENUSAUR_SPRITE_SIZE (3 << 14)
#define VENUSAUR_ID 648

#define BLASTOISE_PALETTE_ID (0 << 12)
#define BLASTOISE_SPRITE_SHAPE (0 << 14)
#define BLASTOISE_SPRITE_SIZE (3 << 14)
#define BLASTOISE_ID 776

#endif

