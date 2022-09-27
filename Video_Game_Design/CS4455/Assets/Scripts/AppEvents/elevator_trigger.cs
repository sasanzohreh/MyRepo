using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class elevator_trigger : MonoBehaviour
{

    public bool stepped_out{ get; private set; }
    private void OnTriggerEnter() {
        stepped_out = true;
    }

}
