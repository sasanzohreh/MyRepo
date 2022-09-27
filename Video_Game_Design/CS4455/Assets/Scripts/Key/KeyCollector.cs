using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class KeyCollector : MonoBehaviour
{
    public bool hasKey = false;
    public int keyCount = 0;

    public void ReceiveKey(){
        keyCount++;
        if (keyCount == 2) {
            hasKey = true;
            Debug.Log("has two keys");
        }

    }

}
