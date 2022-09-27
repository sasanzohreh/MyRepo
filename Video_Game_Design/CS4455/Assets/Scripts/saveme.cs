using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class saveme : MonoBehaviour
{
    //private static saveme player;
    // Start is called before the first frame update
    void Start()
    {

        // if (player == null) {
        if (SceneManager.GetActiveScene() == SceneManager.GetSceneByName("ground level 1") ||
        SceneManager.GetActiveScene() == SceneManager.GetSceneByName("level oscar (below level two") ||
        SceneManager.GetActiveScene() == SceneManager.GetSceneByName("second floor (below top)") ||
        SceneManager.GetActiveScene() == SceneManager.GetSceneByName("demo")) {
            DontDestroyOnLoad(gameObject);
                //player = this;
            // }
        } 
    }

}
