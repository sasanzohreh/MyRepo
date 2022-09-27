using System.Collections;
using System.Collections.Generic;
using UnityEngine.UI;
using UnityEngine;
using UnityEngine.SceneManagement;

public class GameResumer : MonoBehaviour
{
	public Button yourButton;
    public GameObject pauseMenu;

	void Start () {
		Button btn = yourButton.GetComponent<Button>();
		btn.onClick.AddListener(TaskOnClick2);
	}

	void TaskOnClick2(){
        
        pauseMenu.GetComponent<MenuStarter>().deactivate();
	}


}