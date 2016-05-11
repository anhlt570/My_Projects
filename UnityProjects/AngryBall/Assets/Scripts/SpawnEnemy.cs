using UnityEngine;
using System.Collections;

public class SpawnEnemy : MonoBehaviour {
    public float enemyStart;
    public float enemyDelay;
    public GameObject enemy;

	// Use this for initialization
	void Start () {
        enemyStart = Random.Range(1f, 3f);
        enemyDelay = Random.Range(1f, 3f);
        InvokeRepeating("spawnEnemy", enemyStart, enemyDelay);
	}
	
	// Update is called once per frame
	void Update () {
       
	}

    void spawnEnemy()
    {
        Instantiate(enemy, transform.position, transform.rotation);
    }
}
