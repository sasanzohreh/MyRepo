using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Doublsb.Dialog;
using UnityEngine.SceneManagement;

public class DialogFinal : MonoBehaviour
{

    public DialogManager dialogManager;

    public GameObject player;

    // Start is called before the first frame update
    void Start()
    {
        if (SceneManager.GetActiveScene() == SceneManager.GetSceneByName("ground level 1")) {
            DialogData dialogData = new DialogData("I've WON! I've escaped! It's time to go back home.... somehow.........", "Ben");

            if (dialogManager.state == State.Deactivate) {
                dialogManager.Show(dialogData);
            }
        }
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
