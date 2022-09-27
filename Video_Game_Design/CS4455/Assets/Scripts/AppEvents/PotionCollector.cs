using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[RequireComponent(typeof(Animator))]
public class PotionCollector : MonoBehaviour
{
    Transform handHold;
    private Animator anim;
    private bool aim = true;
    public bool hasPotion = false;
    public Rigidbody potionPrefab;
    Rigidbody currPotion;
    public GameObject currPot;
    
    
    public void ReceivePotion(Material mat) {
        hasPotion = true;
        currPotion = Instantiate(potionPrefab, handHold);
        currPotion.transform.localPosition = new Vector3(0,0,0);
        currPotion.isKinematic = true;
        currPot.SetActive(true);
        currPot.GetComponent<Renderer>().material = mat;
        
    }

    void Awake() {
        handHold = this.transform.Find("Group/Main/DeformationSystem/Root_M/Spine1_M/Spine1Part1_M/Spine1Part2_M/Chest_M/Scapula_L/Shoulder_L/Elbow_L/Wrist_L/PotionHoldSpot");
        if (potionPrefab == null) 
            Debug.Log("Potion prefab could not be found");
        anim = GetComponent<Animator>();
        if (anim == null)
            Debug.Log("Animator could not be found");
    }

    void ThrowPotion() {
        currPotion.transform.parent = null;
        currPotion.isKinematic = false;
        currPotion.velocity = new Vector3(0,0,0);
        currPotion.angularVelocity = new Vector3(0,0,0);
        currPotion.AddForce(this.transform.forward * 8f, ForceMode.VelocityChange);
        currPotion = null;
        anim.SetBool("Throw", false);
    }

    void Update() {
        //if (currPotion != null && Input.GetButtonDown("Fire1"))
        if (currPotion != null && Input.GetButtonDown("Fire1")) {
            aim = true;
            anim.SetBool("Throw", true);
        }
        if (anim.GetCurrentAnimatorClipInfo(1).Length > 0) {
            if (anim.GetBool("Throw") && anim.GetCurrentAnimatorClipInfo(1)[0].clip.name == "Throw") {
                //Debug.Log(anim.GetCurrentAnimatorStateInfo(1).normalizedTime % 1);
                if((anim.GetCurrentAnimatorStateInfo(1).normalizedTime % 1) > .3 && (anim.GetCurrentAnimatorStateInfo(1).normalizedTime % 1) < .33 && aim) {
                    PauseAnim();
                }
                if (Input.GetButtonUp("Fire1")) {
                    aim = false;
                    ResumeAnim();
                    //anim.SetBool("Throw", false);
                }
            }
        }
        /*if(Input.GetKeyDown(KeyCode.Space)) {
            anim.SetBool("Interact", true);
        }*/
        /*if(anim["Throwing"].time > .19 && anim["Throwing"].time < .23) {
            PauseAnim();
        }
        if (Input.GetButtonUp("Fire1")) {
            ResumeAnim();
        }*/
        //anim["Throwing"].speed = 0.0f    
        //Debug.Log(currBall != null && Input.GetButtonDown("Fire1"));
        //Debug.Log("Throw + " + anim.GetBool("Throw"));
    }

    void PauseAnim() {
        //anim.GetCurrentAnimatorStateInfo(1).speedMultiplier = 0.0f;   
        anim.SetFloat("speedMult", 0.0f);
    }

    void ResumeAnim() {
        //anim.GetCurrentAnimatorStateInfo(1).speedMultiplier = 1.0f;   
        anim.SetFloat("speedMult", 1.0f);
    }

    void CancelInteract() {
        anim.SetBool("Interact", false);
    }
}
