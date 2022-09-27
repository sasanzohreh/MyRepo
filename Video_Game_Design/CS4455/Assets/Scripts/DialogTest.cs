using System.Collections;
using System.Collections.Generic;
using UnityEngine;

using Doublsb.Dialog;

public class DialogTest : MonoBehaviour
{

    public DialogManager dialogManager;

    [SerializeField]
    GameObject firstKey;

    [SerializeField]
    GameObject elevatorDoor;

    [SerializeField]
    GameObject lever;

    [SerializeField]
    GameObject monster;

    [SerializeField]
    GameObject shelf1;
    [SerializeField]
    GameObject shelf2;
    [SerializeField]
    GameObject pcset1;

    [SerializeField]
    GameObject player;
    KeyCollector kc;
    
    bool alreadyPlaying = false;

    int counter = 0;
    int doorCounter = 0;
    int doorCounter2 = 0;
    int hasEnoughKeys = 0;
    int leverCounter = 0;
    int monsterCounter = 0;
    int oneKey = 0;

    // Start is called before the first frame update
    void Start()
    {

        DialogData dialogData = new DialogData("I think I hear something... /speed:0.2/Wait..." + "/speed:init/is something coming /speed:down/toward me? /close/", "Ben");

        dialogManager.Show(dialogData);

        kc = player.GetComponent<KeyCollector>();
        
    }

    //Update is called once per frame
    void Update()
    {

        if (counter == 0) {
            if (Vector3.Distance(firstKey.transform.position, player.transform.position) <= 15) {

                DialogData dialogData = new DialogData("Wait, did I just see something shining over there? What was that? /close/", "Ben");


                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
                
                counter = 1;
            }
        }

        if (kc.keyCount == 1) {

            if (Vector3.Distance(elevatorDoor.transform.position, player.transform.position) <= 3) {

                DialogData dialogData = new DialogData("This must be the door for the keys... it looks like I need one more... I should keep looking around/speed:down/.... /close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
            }

        } else if (kc.hasKey && doorCounter == 0) {
            if (Vector3.Distance(elevatorDoor.transform.position, player.transform.position) <= 3) {

                DialogData dialogData = new DialogData("I think I can finally open this door/speed:down/! /close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }

                doorCounter++;
            }
        } else if (kc.keyCount == 0) {
            if (Vector3.Distance(elevatorDoor.transform.position, player.transform.position) <= 3) {

                DialogData dialogData = new DialogData("Finally, a door! It looked locked though... maybe I need a ke/speed:down/y....?/close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
            }

        }

        if (hasEnoughKeys == 0 && kc.hasKey) {

            DialogData dialogData = new DialogData("Maybe I have enough keys now... I should get looking for a door for these ASAP/speed:down/! /close/", "Ben");

            if (dialogManager.state == State.Deactivate) {
                dialogManager.Show(dialogData);
            }

            hasEnoughKeys = 1;
        }


        if(Vector3.Distance(lever.transform.position, player.transform.position) <= 3 && leverCounter == 0) {

            DialogData dialogData = new DialogData("Oh God, is the water /speed:0.2/rising in here? /speed:init/Hm... there's a note next to these levers..." +
            " it says, 'You only need to change one lever'........ I need to figure out which one. Preferably before I drown to death...", "Ben");

            if (dialogManager.state == State.Deactivate) {
                dialogManager.Show(dialogData);
            }

            leverCounter++;
        }

        if (monsterCounter == 0 && (Vector3.Distance(monster.transform.position, player.transform.position) <= 8)) {

            DialogData dialogData = new DialogData("WHAT IS THAT! It's huge, it's terrifying... and I think it's chasing after me..../speed:down/! /close/", "Ben");

            if (dialogManager.state == State.Deactivate) {
                dialogManager.Show(dialogData);
            }

            monsterCounter++;
        }

        if (kc.keyCount == 1 && oneKey == 0) {

            DialogData dialogData = new DialogData("Oh... it's a key! If there's one over here, there might be more lying around......../close/", "Ben");

            if (dialogManager.state == State.Deactivate) {
                dialogManager.Show(dialogData);
            }

            oneKey++;
        }

        if (kc.keyCount== 1 && (Vector3.Distance(shelf1.transform.position, player.transform.position) <= 10)) {

            DialogData dialogData = new DialogData("I need to keep looking for keys... I wonder if I'm going the right way....../speed:down/?/close/", "Ben");

            if (dialogManager.state == State.Deactivate) {
                dialogManager.Show(dialogData);
            }
        } else if (kc.hasKey && (Vector3.Distance(shelf1.transform.position, player.transform.position) <= 10)) {

            DialogData dialogData = new DialogData("I wonder if I have enough keys now? I should start looking for a door... maybe it'll be my ticket out of here/speed:down/!/close/", "Ben");

            if (dialogManager.state == State.Deactivate) {
                dialogManager.Show(dialogData);
            }
        } else if (kc.keyCount == 0 && (Vector3.Distance(shelf1.transform.position, player.transform.position) <= 10)) {

            DialogData dialogData = new DialogData("I feel like I need to be searching for something... maybe a door? I just need to find a way out of here and away from that.../speed:down/thing!/close/", "Ben");

            if (dialogManager.state == State.Deactivate) {
                dialogManager.Show(dialogData);
            }
        }

        if (kc.keyCount== 1 && (Vector3.Distance(shelf2.transform.position, player.transform.position) <= 10)) {

            DialogData dialogData = new DialogData("I need to keep looking for keys... I wonder if I'm going the right way....../speed:down/?/close/", "Ben");

            if (dialogManager.state == State.Deactivate) {
                dialogManager.Show(dialogData);
            }
        } else if (kc.hasKey && (Vector3.Distance(shelf2.transform.position, player.transform.position) <= 10)) {

            DialogData dialogData = new DialogData("I wonder if I have enough keys now? I should start looking for a door... maybe it'll be my ticket out of here/speed:down/!/close/", "Ben");

            if (dialogManager.state == State.Deactivate) {
                dialogManager.Show(dialogData);
            }
        } else if (kc.keyCount == 0 && (Vector3.Distance(shelf2.transform.position, player.transform.position) <= 10)) {

            DialogData dialogData = new DialogData("I feel like I need to be searching for something... maybe a door? I just need to find a way out of here and away from that.../speed:down/thing!/close/", "Ben");

            if (dialogManager.state == State.Deactivate) {
                dialogManager.Show(dialogData);
            }
        }

        if (kc.keyCount== 1 && (Vector3.Distance(pcset1.transform.position, player.transform.position) <= 10)) {

            DialogData dialogData = new DialogData("I need to keep looking for keys... maybe there's a door nearby...../speed:down/?/close/", "Ben");

            if (dialogManager.state == State.Deactivate) {
                dialogManager.Show(dialogData);
            }
        } else if (kc.hasKey && (Vector3.Distance(pcset1.transform.position, player.transform.position) <= 10)) {

            DialogData dialogData = new DialogData("I wonder if I have enough keys now? I feel like I might have seen a door somewhere around here/speed:down/!/close/", "Ben");

            if (dialogManager.state == State.Deactivate) {
                dialogManager.Show(dialogData);
            }
        } else if (kc.keyCount == 0 && (Vector3.Distance(pcset1.transform.position, player.transform.position) <= 10)) {

            DialogData dialogData = new DialogData("I need to look for a way out of here and away from that /speed:down/thing! /speed:init/I need to find a door or something......../close/", "Ben");

            if (dialogManager.state == State.Deactivate) {
                dialogManager.Show(dialogData);
            }
        }
        
    }
}
