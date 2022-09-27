using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class JumpingBean : MonoBehaviour
{
    private int groundContactCount = 0;
    public float jumpableGroundNormalMaxAngle = 45f;
    public bool closeToJumpableGround;

    public bool IsGrounded
    {
        get
        {
            return groundContactCount > 0;
        }
    }
    public System.DateTime previousTime;
    public float newTime = 2;

    public Rigidbody rb;
    void Start(){
        rb = GetComponent<Rigidbody>();
        previousTime = System.DateTime.UtcNow;
    }

    void FixedUpdate()
    {
        bool isGrounded = IsGrounded || CharacterCommon.CheckGroundNear(this.transform.position, jumpableGroundNormalMaxAngle, 0.1f, 1f, out closeToJumpableGround);

        if (isGrounded)
        {

            if ((System.DateTime.UtcNow - previousTime).TotalSeconds > newTime){
                //use root motion as is if on the ground		
                rb.AddForce(new Vector3(0, 5, 0), ForceMode.Impulse);
                rb.AddTorque(new Vector3(30,0,0));
            }
        }

    }

    //This is a physics callback
    void OnCollisionEnter(Collision collision)
    {

        if (collision.transform.gameObject.tag == "ground")
        {
            ++groundContactCount;

            previousTime = System.DateTime.UtcNow;
            newTime = Random.Range(2f, 4f);
        }
						
    }

        private void OnCollisionExit(Collision collision)
    {

        if (collision.transform.gameObject.tag == "ground")
        {
            --groundContactCount;
        }

    }
}
