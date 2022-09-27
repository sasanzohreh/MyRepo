using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class PotionTarget : MonoBehaviour
{
    public ChemistryBehavior player;
    void Awake(){
        player = GameObject.Find("BlueSuitFree01").GetComponent<ChemistryBehavior>();
        if (player == null)
            Debug.Log("Player not found on level 3 monster");
    }
    void OnTriggerEnter(Collider c) {
        Debug.Log(c.gameObject.layer);
        if(c.gameObject.layer == 14){
            int[] actualOrder = player.playersOrder;
            int[] expectedOrder = {2, 3, 1};
            bool equals = true;
            //Debug.Log(actualOrder);
            for(int i = 0; i < 3; i++) {
                //Debug.Log(actualOrder[i]);
                if (actualOrder[i] != expectedOrder[i]) {
                    equals = false;
                    break;
                }
            }
            if (equals) {
                Destroy(this.gameObject);
                Destroy(c.gameObject);
                player.kills = player.kills + 1;
                SceneManager.LoadScene("ground level 1"); 
            } else {
                GetComponent<UnityEngine.AI.NavMeshAgent>().speed = GetComponent<UnityEngine.AI.NavMeshAgent>().speed * 1.1f;
            }
        }
    }
}
