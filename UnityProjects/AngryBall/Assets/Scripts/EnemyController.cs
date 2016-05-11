using UnityEngine;
using System.Collections;

public class EnemyController : MonoBehaviour {
    public float enemySpeed = 2;
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
        transform.Translate(Vector3.up * enemySpeed * Time.deltaTime);
        if (transform.position.y <=-5f) Destroy(this.gameObject);
	}
}
