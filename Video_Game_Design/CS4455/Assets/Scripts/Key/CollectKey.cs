using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CollectKey : MonoBehaviour
{
        void OnTriggerEnter(Collider c) {

        if (c.attachedRigidbody != null) {
            KeyCollector kc = c.attachedRigidbody.gameObject.GetComponent<KeyCollector>();

            if (kc != null) {
                EventManager.TriggerEvent<BombBounceEvent, Vector3>(c.transform.position);
                Destroy(this.gameObject);
                kc.ReceiveKey();
            }

        }


    }
}
