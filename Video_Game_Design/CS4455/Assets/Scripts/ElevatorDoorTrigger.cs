using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ElevatorDoorTrigger : MonoBehaviour {

    [SerializeField] private Animator myAnimationController;

    [SerializeField]
    private GameObject player;
    KeyCollector kc;
    [SerializeField]
    public Camera myCam;


    void Start() {
        Debug.Log("getting the KeyCollector");
        kc = player.GetComponent<KeyCollector>();
    }

    void Update() {
        RaycastHit raycastHit;

        if (Input.GetKeyDown(KeyCode.Space)) {
            Debug.Log("hello");
            var ray = myCam.ScreenPointToRay(Input.mousePosition);

            if (Physics.Raycast(ray, out raycastHit, 100f)) {
                Debug.Log("clicked on door");
                if (kc != null) {
                    Debug.Log("kc exists");
                    if (kc.hasKey) {
                        myAnimationController.SetBool("hasClicked", true);
                    } else {
                        Debug.Log("Not enough keys");
                    }
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
