using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MenuStarter : MonoBehaviour
{
    [SerializeField] private GameObject pauseMenuUI;
    // Start is called before the first frame update
    void Start()
    {
        deactivate();
    }

    // Update is called once per frame
    void Update()
    {
    }

    public void activate(){
        Time.timeScale = 0;
        pauseMenuUI.SetActive(true);
    }

    public void deactivate(){
        Time.timeScale = 1;
        pauseMenuUI.SetActive(false);
    }
}
