using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using Doublsb.Dialog;

public class DialogGround : MonoBehaviour
{

    public DialogManager dialogManager;

    [SerializeField]
    public GameObject player; 

    GameObject firstTrigger;
    int counter = 0;

    bool destroyed;

    GameObject groundMonster;
    int monsterCounter = 0;

    int destroyedMonster = 0;

    // Start is called before the first frame update
    void Start()
    {

        
    }

    // Update is called once per frame
    void Update()
    {

        if (SceneManager.GetActiveScene() == SceneManager.GetSceneByName("level oscar (below level two)")) {
            
            firstTrigger = GameObject.Find("First Trigger");
            groundMonster = GameObject.Find("Second Floor Enemy");
            if (groundMonster != null)
                destroyed = false;
            else 
                destroyed = true;

            if (counter == 0 && Vector3.Distance(firstTrigger.transform.position, player.transform.position) <= 2) {
                DialogData dialogData = new DialogData("This floor is crazy dark! Good thing I have this flashlight... I should turn it on if I want to navigate this floor properly... " +
                "Maybe there's finally a way to escape from this place on this floor/speed:down/./close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }

                counter = 1;
            }

            if (counter == 1 && Vector3.Distance(firstTrigger.transform.position, player.transform.position) <= 2) {
                DialogData dialogData = new DialogData("Is the elevator..... closed?!? This has never happened before. Oh God, am I stuck here? If it's like this.... it makes it feel like I need " + 
                "to get this elevator to open again for me to actually escape..../speed:down/.../close/", "Ben");
                
                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
            }

            if (counter == 2 && Vector3.Distance(firstTrigger.transform.position, player.transform.position) <= 1) {
                DialogData dialogData = new DialogData("That monster is gone! Hopefully this elevator door finally opens...... and I can get out of here/speed:down/!/close", "Ben");
                
                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }
            }

            if (!destroyed) {

                if (Vector3.Distance(groundMonster.transform.position, player.transform.position) <= 8 && monsterCounter == 0) {
                    DialogData dialogData = new DialogData("Oh no, oh no, OH NO.... the monster... it IS on this level...... thank God I have this potion. As long as I've made it right, I think I " +
                    "have a fighting chance......... I hope......./close/", "Ben");

                    if (dialogManager.state == State.Deactivate) {
                        dialogManager.Show(dialogData);
                    }

                    monsterCounter = 1;
                }

                if (Vector3.Distance(groundMonster.transform.position, player.transform.position) <= 4 && monsterCounter == 1) {
                    DialogData dialogData = new DialogData("I need to throw everything I've got at this thing.... and if I don't have any confidence, then I need to run!.... /close/", "Ben");

                    if (dialogManager.state == State.Deactivate) {
                        dialogManager.Show(dialogData);
                    }                    
                } 
            }

            //is destroyed
            if (destroyed && destroyedMonster == 0) {
                DialogData dialogData = new DialogData("Is..... is it gone? I..... I hope it's gone forever. I'm terrified. Maybe I should make my way back to the elevator............/close/", "Ben");

                if (dialogManager.state == State.Deactivate) {
                    dialogManager.Show(dialogData);
                }

                destroyedMonster = 1;
            }
        }
        
    }
}
