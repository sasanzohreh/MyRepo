using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Panel_Opener : MonoBehaviour
{
    public GameObject Panel;
    public GameObject button_close;

    public void OpenPanel(){
        
        if(Panel!=null){

            Panel.SetActive(true);
            //pauses game while panel is open
            Time.timeScale = 0;
        }

        if(button_close!=null){

            button_close.SetActive(true);
        }
    }
}
