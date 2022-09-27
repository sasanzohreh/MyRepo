using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using Doublsb.Dialog;

public class DialogMiddle : MonoBehaviour
{

    //[SerializeField]
    public DialogManager dialogManager;
    
    public GameObject player;
    //public GameObject playerSphere;

    //[SerializeField]
    GameObject note1;
    GameObject note2;
    GameObject note3;
    GameObject note4;
    GameObject note5;
    GameObject note6;
    GameObject note7;
    GameObject note8;
    GameObject note9;
    GameObject note10;
    GameObject note11;
    GameObject note12;
    GameObject note13;

    GameObject firstTrigger;
    GameObject secondTrigger;
    GameObject chemTrigger;
    GameObject thirdTrigger;
    GameObject fourthTrigger;

    int counter = 0;
    int counter2 = 0;
    int chemCounter = 0;
    int counter3 = 0;
    int counter4 = 0;
    
    // Start is called before the first frame update
    void Start()
    {

        //player = GameObject.Find("BlueSuitFree01");
        
    }

    // Update is called once per frame
    void Update()
    {

        if (SceneManager.GetActiveScene() == SceneManager.GetSceneByName("second floor (below top)")) {
            
            note1 = GameObject.Find("Notes 1");
            note2 = GameObject.Find("Notes 2");
            note3 = GameObject.Find("Notes 3");
            note4 = GameObject.Find("Notes 4");
            note5 = GameObject.Find("Notes 5");
            note6 = GameObject.Find("Notes 6");
            note7 = GameObject.Find("Notes 7");
            note8 = GameObject.Find("Notes 8");
            note9 = GameObject.Find("Notes 9");
            note10 = GameObject.Find("Notes 10");
            note11 = GameObject.Find("Notes 11");
            note12 = GameObject.Find("Notes 12");
            note13 = GameObject.Find("Notes 13");
            
            firstTrigger = GameObject.Find("First Trigger");
            secondTrigger = GameObject.Find("Second Trigger");
            chemTrigger = GameObject.Find("Chem Trigger");
            thirdTrigger = GameObject.Find("Third Trigger");
            fourthTrigger = GameObject.Find("Fourth Trigger");

            if (counter == 0 && Vector3.Distance(firstTrigger.transform.position, player.transform.position) <= 2) {
                DialogData dialogData = new DialogData("Oh thank God I escaped that monster..... but where am I now? I was too distracted earlier, but this place looks a lot like my office..." + 
                    "/speed:down/.../close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }

                counter++;

            }

            if (Vector3.Distance(note1.transform.position, player.transform.position) <= 1) {
                DialogData dialogData = new DialogData("Oh, it looks like there are some notes lying around... maybe I should take a look around. " + 
                    "It could help me figure out what's going on..../speed:down/..../close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
            } 

            if (Vector3.Distance(note2.transform.position, player.transform.position) <= 1) {
                DialogData dialogData = new DialogData("It looks like somebody's journal... 'I've been here for so long, I barely remember who I am anymore. I just... took one wrong step, somehow, and ended up in this demonic facsimile of my own " +
                    "office building....................' (1 out of 12)/close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
            } 
                        
            
            if (Vector3.Distance(note3.transform.position, player.transform.position) <= 1) {
                DialogData dialogData = new DialogData("'It feels like you can wander these halls for days, months, maybe even years. I'm too scared to go back to that elevator. I barely escaped with my life " +
                    "upstairs... I have a feeling I can't return back up there anyway. That means the only way out... is down.' (2 out of 12)/close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
            } 
            
            if (Vector3.Distance(note4.transform.position, player.transform.position) <= 1) {
                DialogData dialogData = new DialogData("'I've been looking around, and it looks like whoever was here before me was in a similar situation. It seems like getting here" + 
                    " is the equivalent of no-clipping from existence. I've only heard of some stupid rumors about the 'backrooms'....... although I guess it's not just a rumor to me " + 
                    "anymore.' (3 out of 12)/close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
            }

            if (Vector3.Distance(secondTrigger.transform.position, player.transform.position) <= 2 && counter2 == 0) {
                DialogData dialogData = new DialogData("I can't believe others have gotten stuck like this! No-clipping... that's crazy. But at this point.... how can I not believe them/speed:down/?/close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }

                counter2++;
            }

            if (Vector3.Distance(note5.transform.position, player.transform.position) <= 1) {
                DialogData dialogData = new DialogData("‘Thankfully there’s nothing like that... thing... on this floor. Even so, there’s no telling what could be on the next floor down that elevator." +
                " I’m terrified. I don’t know what to do. Can I do anything, make anything... to protect myself?' (4 out of 12)/close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
            }

            if (Vector3.Distance(chemTrigger.transform.position, player.transform.position) <= 2 && chemCounter == 0) {
                DialogData dialogData = new DialogData("A chemistry set? It was never my best subject. I should probably avoid messing with it until I know what I'm doing... Maybe there's a note " +
                "lying around that can give me a clue/speed:down/?/close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
            } else if (Vector3.Distance(chemTrigger.transform.position, player.transform.position) <= 2 && chemCounter == 1) {
                DialogData dialogData = new DialogData("Hm.... from that note, Rd goes last, and there's a logical order for pouring these. I wonder what kind of order that'd be, though?", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
            }

            if (Vector3.Distance(note6.transform.position, player.transform.position) <= 1) {
                DialogData dialogData = new DialogData("'I'm getting desperate. It's strange. I don't think I can starve here, or even get thirsty. At the same time... " +
                "even if I can't die from that, I feel like I'll die of loneliness. When was the last time I spoke to anybody besides myself? Do I take a chance... and leave?' (5 out of 12)/close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
            }

            if (Vector3.Distance(note7.transform.position, player.transform.position) <= 1) {
                DialogData dialogData = new DialogData("‘To be honest, there’s a small glimmer of hope. Although this floor has next to nothing.... It has this weird looking.... chemistry set. " +
                " I’ve avoided touching it for a long time, but now I’m wondering if I can use it somehow....’ (6 out of 12)/close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
            }

            if (Vector3.Distance(note8.transform.position, player.transform.position) <= 1) {
                DialogData dialogData = new DialogData("‘I’ve had an inkling for a little bit that whoever was here before me.... they might have been a chemist. " +
                "Scattered around this floor are papers scrawled with notes on what chemicals could possibly be on that table. Maybe he left some instructions somewhere?’ (7 out of 12)/close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
                
            }

            if (Vector3.Distance(note9.transform.position, player.transform.position) <= 1) {
                DialogData dialogData = new DialogData("‘I could also always just make my way back to that elevator... and take my chances. After all, is there actually another monster?’ (8 out of 12)/close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
                counter3++;
            }

            if (Vector3.Distance(thirdTrigger.transform.position, player.transform.position) <= 2 && counter3 == 1) {
                DialogData dialogData = new DialogData("That note is right.... If there’s no monster on this floor.... Maybe there’s nothing on the next floor either." +
                " I could keep looking around for stuff on the chemistry set, but there’s no telling what could happen if I make it wrong, either/speed:down/./close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
            }

            if (Vector3.Distance(note10.transform.position, player.transform.position) <= 1) {
                DialogData dialogData = new DialogData("'I’ve spent a few.... Weeks....? It’s so hard to tell what the passage of time is like. I’ve spent a few weeks decoding the chemist’s notes.'" +
                " (9 out of 12)/close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
            }

            if (Vector3.Distance(note11.transform.position, player.transform.position) <= 1) {
                DialogData dialogData = new DialogData("'From what I can understand, the chemicals are Rd, Bl, and Gr.... his chicken-scratch is hard to decipher, but from what I can piece together, I know Rd goes last... " +
                " And there’s a logical pattern for pouring them. Outside of that, I can understand nothing else.' (10 out of 12)/close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
                counter4++;
                chemCounter = 1;
            }

            if (Vector3.Distance(fourthTrigger.transform.position, player.transform.position) <= 2 && counter4 > 0) {
                DialogData dialogData = new DialogData("Maybe I should make my way back to the chemistry set now. That was a pretty helpful note... I could probably construct something with them now. " +
                "There could also be more notes lying around, though/speed:down/./close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
            }

            if (Vector3.Distance(note12.transform.position, player.transform.position) <= 1) {
                DialogData dialogData = new DialogData("'I’ve used the chemistry set. I’m only making a little bit... this feels like do or die. Besides, there’s no telling if somebody else could use " +
                "it if they’re unlucky enough to be forsaken here.' (11 out of 12)/close/", "Ben");
                
                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }

            }

            if (Vector3.Distance(note13.transform.position, player.transform.position) <= 1) {
                DialogData dialogData = new DialogData("'I’m scared... I’ve pressed my ear to the floor, and I swear I can hear something stomping around down there... but I’m not interested in going insane here, " + 
                "either.' (12 out of 12)/close/", "Ben");
                
                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
                
            }

        }
        
    }
}
