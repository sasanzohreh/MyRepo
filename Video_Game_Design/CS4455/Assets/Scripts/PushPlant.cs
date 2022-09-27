using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PushPlant : MonoBehaviour
{
    [SerializeField]
    public Animator anim;

    [SerializeField]
    public Camera myCam;

    void Update() {

        RaycastHit raycastHit;

        if (Input.GetMouseButtonDown(0)) {
            var ray = myCam.ScreenPointToRay(Input.mousePosition);

            if (Physics.Raycast(ray, out raycastHit, 100f)) {
                if (raycastHit.collider.tag == "keyPlant") {
                    Debug.Log("hello");
                    anim.SetBool("hasClicked", true);
                }

            }
        }

    }
}
