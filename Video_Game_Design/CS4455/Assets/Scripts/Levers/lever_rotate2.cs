using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class lever_rotate2 : MonoBehaviour
{
    // Start is called before the first frame update
    public bool lever_toggle;
    public GameObject lever;

    public Vector3 originalRotation;
    public GameObject player;
    private Animator anim;

    void Start()
    {
        lever_toggle=true;
        
    }
    void Awake(){
        anim = player.GetComponent<Animator>();
        if (anim == null)
            Debug.Log("Animator could not be found");
    }

    // Update is called once per frame
    void Update()
    {
        
        if (Input.GetKeyDown(KeyCode.O))
        {
            if(lever_toggle==true)
            {
                lever_toggle=false;
                Debug.Log("Number 2 False");
                lever.transform.position = lever.transform.position + new Vector3(0f,0.1f,0f);
                //lever.transform.eulerAngles = lever.transform.eulerAngles + new Vector3(30,0,0);
                Debug.Log(lever.transform.position);
                anim.SetBool("Interact", true);
                

            }
            else if(lever_toggle==false)
            {
                lever_toggle=true;
                Debug.Log("Number 2 True");
                lever.transform.position = lever.transform.position + new Vector3(0f,-0.1f,0f);
               //lever.transform.eulerAngles = lever.transform.eulerAngles + new Vector3(-30,0,0);
                //lever.transform.rotation =  lever.transform.Rotate(90.0f, 0.0f, 0.0f, Space.World);
               // lever.transform.rotation.x = 30;
                Debug.Log(lever.transform.position);
                anim.SetBool("Interact", true);
            }



        }

        
        
        
    }
}
