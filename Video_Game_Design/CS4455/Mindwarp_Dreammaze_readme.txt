Team: Mindwarp

Names: Alice Wang, Madhumitha Murali, Marie Ow, Yihe Liu, Sasan Zohreh

Start Scene File: Start Screen

--

Note: There is dialog, in-game, that gives some measure of guidance for the player. Below are specific notes to follow to encounter events, such as collecting keys.

TOP FLOOR:
You will begin on the top level of the building. From here, you will need to explore the top floor and find hidden keys. One key is hidden behind a plant (you can click to move the plant, or directly walk over it (i.e. collide)) in order to get the key. From where you initially start, you want to turn left, left again, and right ahead should be a large plant. This is where the first key is. To get the second key, you want to continue to go forward from the plant toward where there are more desks -- the second key is in plain sight, on the floor. After you retrieve both keys, you will be able to open the door to the elevator. To open the door, you need to press space for it to swing open.

Note that the entire time you are on this floor, a monster AI is also chasing you. The AI “colliding” with you will result in death. But the AI does follow you and can predict where exactly you're likely to move next.

After you open the door to the elevator, you'll enter a room that is flooding. Manipulate the levers in order to open the elevator door. 

*The controls for the levers are I, O, and P to toggle them. For our build, if you press the "O" key (i.e. toggle the second lever), the elevator doors should open. If you manipulate other levers on accident, like I or P, you need to re-toggle them again. 


SECOND FLOOR:
For this floor, the main goal is to interact with a chemistry set that’s on this floor. Lying around throughout the level are open notebooks and clipboards; walking over these notes will trigger dialog text that the user is “reading.” You will have to explore the floor thoroughly in order to find the note that contains the hint for how to make the chemical mix.

Once you find the note, you can make your way back to the chemistry set and create a potion. (Note you only have one try, essentially.) Interacting with this set will allow you to make a chemical that you’ll be able to throw; aiming and throwing this at the monster will kill it. Please note that there is no monster on this floor. Instead, it is meant to be more of an explorative floor for the player to a.) solve a puzzle and b.) find out more about the situation.
From the elevator, you will want to go straight, take the first right, go straight, and then take a left-- straight ahead should be the chemistry set. To interact with the set, you can click on the R, G, and B keys, respectively. R will control the red potion, G will control the green potion, and B will control the blue potion. Click them in the order you’d like to pour. Once you finish pouring the three bottles, you can press T to fill the empty potion bottle. Pressing space will then allow you to pick it up. 

To aim and throw, you can press the left control key. (This won’t be necessary at this time, though, since there’s no monster on this floor.)


THIRD FLOOR:
For this floor, it is meant to be dark and difficult for the player to see. You can press F to toggle the flashlight on and off. All the while, there is also a monster hunting to find you as well. The main goal is to fight your way out using only a flashlight to light your way. To destroy the monster, you had to make the correct potion on the floor above. If you aim it properly and hit the monster, then you will immediately be transferred to a free floor– you have won!!


OVERALL CONTROLS:

Left mouse click: You can quickly click through dialog using the left mouse click. Note that some dialog only appears once, however. Furthermore, some dialog is automatically closed, whereas some is left up until the player clicks on the dialog box. The boxes left up usually contain hints, which we left up so that the player can continue to reference the hint at their discretion.

I, O, P keys: to interact with the levers, from left to right

R, G, B keys: to interact with the potions

T key: fill empty potion bottle

Spacebar: Opens doors, picks up potion

Left control: Hold to aim and throw

F: to trigger the flashlight



Manifest:

Team member: Alice Wang
-> Responsible for chemistry set functionality, some of the behavior for the doors, as well as making the AI enemy work for the top and second floor (Chemistry Set, Door Scripts, Enemy Script (AI)); also responsible for the triggered dialog that is available on the different floors. 
Scripts: ChemistryBehavior.cs, TriggerDoor.cs scripts, EnemyMovement.cs, DialogTest.cs, DialogMiddle.cs, DialogGround.cs, EnemyCollide.cs


Team member: Marie Ow
-> Responsible for top floor key pickup functionality, animation and working of the door when associating with key press, ability to interact with plant with click.
Scripts: Key script (CollectKey, KeyCollector scripts), plant animation, door opening functionality on top floor


Team member: Sasan Zohreh
-> Responsible for the refinement of character movement (running, walking, etc.) and hooking up those movements to the new model asset. Also responsible for coding character throwing functionality and linking that with picking up a potion and throwing it, as well as interaction with monster. Implemented character animation for interacting with levers and potions.
Assets: Player character
Scripts: Character Control (RootMotionControl, CharacterInputController), Character Throwing, Potion pickup behavior


Team member: Yihe Liu
-> Responsible for much of the design of the game (such as implementation of the building and layout), built the last floor (the dark floor) along with flashlight functionality. Also responsible for the elevator coding as well. Also implemented respawn behavior and edited some of the enemy collision behavior.
Scripts: Elevator.cs scripts, Flashlight.cs, EnemyCollide.cs, saveme.cs


Team member: Madhumitha Murali
Main Contributions: One of the mini-challenges in the top most level, the flooding of the room, and switching up a certain combination of the levers to unlock the door to the elevator to escape to the lower level. Also, implemented sound effects for the main character, the lever challenge room and the background music for the entire game. Contributed to developing the UI for hints.
Assets implemented: water, Lever_1, Lever_2, Lever_3 and respective handles (handle_1, handle_2, handle_3), grey door to the elevator (door), Audio sources (for water, character footsteps, background music - (imported from Unity Asset Store))
Scripts: water_level.cs, lift_door.cs, lever_rotate.cs, lever_rotate2.cs, lever_rotate3.cs, Panel_Opener.cs, Panel_Controller.cs