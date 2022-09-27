using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CollectKey2 : MonoBehaviour
{
        void OnTriggerEnter(Collider c) {

        if (c.attachedRigidbody != null) {
            KeyCollectorTwo kc = c.attachedRigidbody.gameObject.GetComponent<KeyCollectorTwo>();

            if (kc != null) {
                EventManager.TriggerEvent<BombBounceEvent, Vector3>(c.transform.position);
                Destroy(this.gameObject);
                kc.ReceiveKeyTwo();
            }

        }


    }
}
