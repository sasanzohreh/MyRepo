using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class elevator_trigger_1 : MonoBehaviour
{
    public GameObject door;
    public bool stepped_out{ get; private set; }
    private void OnTriggerEnter() {
        stepped_out = true;
        door.GetComponent<close_door>().cl();
    }

}
