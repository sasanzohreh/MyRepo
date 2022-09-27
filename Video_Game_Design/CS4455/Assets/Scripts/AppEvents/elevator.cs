using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class elevator : MonoBehaviour
{
    public GameObject moveplatform;
    public GameObject ele_tri;
    private elevator_trigger tr;
    private float t = 0.0f;
    public bool stepped_out = false;
    public float distance;
    private float currDoor;
    public GameObject door_1;
    public GameObject door_2;
    public GameObject door_3;
    public ChemistryBehavior player;
    public PotionCollector playerC;
    public int kills;
    private GameObject door1;

    void Start()
    {
        tr = ele_tri.GetComponent<elevator_trigger>();
    }

    void Awake() {
        player = GameObject.Find("BlueSuitFree01").GetComponent<ChemistryBehavior>();
        playerC = GameObject.Find("BlueSuitFree01").GetComponent<PotionCollector>();
    }

    private void OnTriggerEnter() {
        if (tr != null){
            if (tr.stepped_out){
                t = 5;
        }
        Debug.Log(SceneManager.GetActiveScene().name);
        Debug.Log(player.kills);
        if (player != null){
            kills = player.kills;
            Debug.Log(kills);
        }
        else
            Debug.Log("player not found");
        if (SceneManager.GetActiveScene().name == "demo"){
            SceneManager.LoadScene("second floor (below top)");
            door1 = door_1;
        }
        else if (SceneManager.GetActiveScene().name == "second floor (below top)" && playerC.hasPotion){
            SceneManager.LoadScene("level oscar (below level two)");     
            door1 = door_2;
        }
       
        else if (SceneManager.GetActiveScene().name == "level oscar (below level two)" && kills > 0){
            SceneManager.LoadScene("ground level 1");     
            door1 = door_3;
        }

        currDoor = 2.0f;
        

        }
    }

    void Update() {
        if(currDoor>0){
            
            door1.transform.position = door1.transform.position - new Vector3(0f,0f,0.03f);
            currDoor-=0.03f;
        }
        if (t>Time.deltaTime){
            t = t - Time.deltaTime;
            moveplatform.transform.position -= moveplatform.transform.up * Time.deltaTime /5 * distance;
        }
        kills = player.kills;
    }
}
