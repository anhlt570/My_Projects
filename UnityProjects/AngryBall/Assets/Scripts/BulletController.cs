using UnityEngine;
using System.Collections;

public class BulletController : MonoBehaviour {
    public float speed=10;
	// Use this for initialization
	void Start () {
      //  Enermy = new GameObject[2];
	}
	
	// Update is called once per frame
	void Update () 
    {
        transform.Translate(Vector3.up * speed * Time.deltaTime);
        if (transform.position.y > 6)
        {
            Destroy(this.gameObject);
        }
        
    }
    void OnCollisionEnter2D(Collision2D coll)
    {
        if (coll.gameObject.tag == "enemy")
        {
            Destroy(coll.gameObject);
            Destroy(this.gameObject);
        }
    }

}

