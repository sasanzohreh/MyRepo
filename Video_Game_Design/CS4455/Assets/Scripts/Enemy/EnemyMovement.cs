using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.AI;

[RequireComponent(typeof(NavMeshAgent))]
public class EnemyMovement : MonoBehaviour
{
    
    [SerializeField]
    public NavMeshAgent agent;

    //[SerializeField]
    public GameObject player;
    public float updateSpeed = 0.1f;

    private void Awake() {
        
        agent = GetComponent<NavMeshAgent>();
        player = GameObject.FindWithTag("Player");
        
    }

    private void Start() {

        StartCoroutine(FollowTarget());
    }

    private IEnumerator FollowTarget() {

        WaitForSeconds Wait = new WaitForSeconds(updateSpeed);

        while (enabled) {
            agent.SetDestination(player.transform.position);
            
            yield return Wait;
        }

    }

}
