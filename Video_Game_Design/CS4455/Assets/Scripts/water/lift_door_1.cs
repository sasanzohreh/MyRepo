using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[RequireComponent(typeof(GameObject),typeof(GameObject))]
[RequireComponent(typeof(bool))]
public class lift_door_1 : MonoBehaviour
{
    public GameObject door1;
    private float currDoor, maxDoor;

    Vector3 originalScale, destinationScale;
    Vector3 originalPosition;
    // Start is called before the first frame update
    void Start()
    {

        originalScale = door1.transform.localScale;
        //destinationScale = new Vector3(6.0f, 6.0f, 12.0f);
        originalPosition =  door1.transform.position;
         
        maxDoor = 2.0f;
        currDoor = 0.0f;

    }

    // Update is called once per frame

    void Update()
    {

        
        

         if(currDoor<maxDoor){
            
        door1.transform.position = door1.transform.position + new Vector3(0f,0f,0.01f);
        currDoor+=0.01f;
        //water_body.transform.localScale = water_body.transform.localScale + new Vector3(0f,0.0002f,0f);
        }

        /*
        if(currDoor<maxDoor && lever==true){
            
        door1.transform.position = door1.transform.position + new Vector3(0f,0f,0.1f);
        //door1.transform.position = new Vector3(0f,0f,0.0f);
        currDoor+=0.1f;
        //water_body.transform.localScale = water_body.transform.localScale + new Vector3(0f,0.0002f,0f);
        }
        */
    }
}


