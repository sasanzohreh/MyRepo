using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

[RequireComponent(typeof(GameObject))]
public class water_level : MonoBehaviour
{

    public GameObject water_body;
    public AudioSource water_splash;
    Vector3 originalScale, destinationScale;
    Vector3 originalPosition;
    // Start is called before the first frame update
    void Start()
    {

        originalScale = water_body.transform.localScale;
        destinationScale = new Vector3(6.0f, 6.0f, 12.0f);
        originalPosition =  water_body.transform.position;
         
        //float currentTime = 0.0f;
    }

    // Update is called once per frame

    void FixedUpdate()
    {
        water_body.transform.position = water_body.transform.position + new Vector3(0f,0.00005f,0f);
        water_body.transform.localScale = water_body.transform.localScale + new Vector3(0f,0.0001f,0f);
        Vector3 local = water_body.transform.localScale;
        if (!water_splash.isPlaying)
        {
            water_splash.Play();
        }

        //Debug.Log(local);
        if (local.y >= 1.5) //Height of the guy
        {
            SceneManager.LoadScene("Death Screen");
            Time.timeScale = 0f;
            //SceneManager.LoadScene("Death Screen_Profiles");
        }
        //SceneManager.LoadScene("");
    }
}
