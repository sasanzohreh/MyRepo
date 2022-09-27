using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TriggerDoor : MonoBehaviour {

    [SerializeField] private Animator myAnimationController;
    private GameObject player;
    KeyCollector kc;

    void Start() {

        player = GameObject.FindWithTag("Player");
        myAnimationController = GetComponent<Animator>();
    }

    private void OnTriggerEnter(Collider c) {

        //I'm not sure if I should comment this out but for now, it seems to be working
        if (c.attachedRigidbody != null) {
            kc = c.attachedRigidbody.gameObject.GetComponent<KeyCollector>();
        }

        if (c.CompareTag("Player")) {

            if (kc != null) {
                if (kc.hasKey) {
                    myAnimationController.SetBool("PlayerEntered", true);
                }
            } 
        }
        

    }

    private void OnTriggerExit(Collider c) {

        if (c.CompareTag("Player")) {

            myAnimationController.SetBool("PlayerEntered", false);
        }
    }

}
