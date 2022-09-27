using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CollectablePotion : MonoBehaviour
{
    public Rigidbody collector;
    public ChemistryBehavior cb;
    void FixedUpdate() {
        if(Vector3.Distance(collector.transform.position, this.transform.position) < 7.0f){
            if (collector != null && Input.GetKeyDown(KeyCode.Space) && cb.allMixed) {
                PotionCollector bc = collector.gameObject.GetComponent<PotionCollector>();
                if (bc != null) {
                    EventManager.TriggerEvent<BombBounceEvent, Vector3>(collector.transform.position);
                    Destroy(this.gameObject);
                    bc.ReceivePotion(cb.mat);
                }
            }
        }
    }
}
