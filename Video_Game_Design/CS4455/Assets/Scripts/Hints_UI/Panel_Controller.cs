using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Panel_Controller : MonoBehaviour
{
    public GameObject Panel;
    public GameObject button_close;

    public void OpenPanel(){
        
        if(Panel!=null){

            Panel.SetActive(false);
            //resumes game
            Time.timeScale = 1;
        }

        if(button_close!=null){

            button_close.SetActive(false);
        }
    }
}
