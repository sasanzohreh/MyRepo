using System.Collections;
using System.Collections.Generic;
using UnityEngine;


public class Pause : MonoBehaviour
{
    public static bool pauseGame = true;
    public static bool start = true;
    void Update()
    {
        if (start){
            PauseController();
            start = false;
        }
        if (Input.GetKeyDown(KeyCode.P))
        {
            pauseGame = !pauseGame;
            PauseController();
        }
    }
    void PauseController ()
    {
        if(!pauseGame)
        {
            Time.timeScale = 1;
        }
        else 
        {
            Time.timeScale = 0f;
        }
    }
}
