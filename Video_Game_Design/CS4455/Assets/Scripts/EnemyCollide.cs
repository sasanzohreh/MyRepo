using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class EnemyCollide : MonoBehaviour
{
    public GameObject pauseMenu;
    public bool monsterDestroyed = false;
    lift_door_monster elevator;

    void PauseGame ()
    {
        Time.timeScale = 0;
    }
    void ResumeGame ()
    {
        Time.timeScale = 1;
    }    
    void OnTriggerEnter(Collider c) {

        elevator = GameObject.FindWithTag("ground door").GetComponent<lift_door_monster>();

        if (c.attachedRigidbody != null) {
            KeyCollector kc = c.attachedRigidbody.gameObject.GetComponent<KeyCollector>();

            if (kc != null) {
                Destroy(gameObject);
                elevator.op();
                monsterDestroyed = true;
                pauseMenu.GetComponent<MenuStarter>().activate();
                 
            }
        }
    }


}
