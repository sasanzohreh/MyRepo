using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Collectables : MonoBehaviour
{
    [SerializeField] private Animator myAnimationController;

    void OnTriggerEnter(Collider c) {
        if (c.attachedRigidbody != null){
            myAnimationController.SetBool("playJump", true);
        }
    }

    void OnTriggerExit(Collider c) {
        if (c.attachedRigidbody != null ){
            myAnimationController.SetBool("playJump", false);
        }
    }
}
