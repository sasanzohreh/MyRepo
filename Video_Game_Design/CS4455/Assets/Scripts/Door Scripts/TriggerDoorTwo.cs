using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TriggerDoorTwo : MonoBehaviour
{
    [SerializeField] private Animator myAnimationController;
    private GameObject player;
    KeyCollectorTwo kc;

    void Start() {

        player = GameObject.FindWithTag("Player");
        myAnimationController = GetComponent<Animator>();
        myAnimationController.SetBool("PlayerEntered", false);
    }

    private void OnTriggerEnter(Collider c) {

        //I'm not sure if I should comment this out but for now, it seems to be working
        if (c.attachedRigidbody != null) {
            kc = c.attachedRigidbody.gameObject.GetComponent<KeyCollectorTwo>();
        }

        if (c.CompareTag("Player")) {

            if (kc != null) {
                if (kc.hasKey2) {
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
