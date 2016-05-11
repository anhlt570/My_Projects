using UnityEngine;
using System.Collections;
using System;
public class PlayerController : MonoBehaviour {
   public float speed = 5;
   public float bulletDeltaTime;
	// Use this for initialization
   public GameObject Bullet;
	void Start () {
        bulletDeltaTime = 0;
        
	}
	
	// Update is called once per frame
    void Update()
    {
        if (Input.GetKey(KeyCode.UpArrow)) transform.Translate(Vector3.up * speed * Time.deltaTime);
        else if (Input.GetKey(KeyCode.DownArrow)) transform.Translate(Vector3.down * speed * Time.deltaTime);
        else if (Input.GetKey(KeyCode.LeftArrow)) transform.Translate(Vector3.left * speed * Time.deltaTime);
        else if (Input.GetKey(KeyCode.RightArrow)) transform.Translate(Vector3.right * speed * Time.deltaTime);
        if (Input.GetKeyDown(KeyCode.Space))
        {
            Instantiate(Bullet, transform.position, Quaternion.identity);
        }

        //Vector3 translate=  new Vector3(Input.mousePosition.x- transform.position.x,Input.mousePosition.y- transform.position.y,0);
        //translate = translate.normalized;
        //translate.z = 0;
        //transform.Translate(translate * 2 * Time.deltaTime);
       
        if (Input.GetMouseButton(0)) Instantiate(Bullet, transform.position, Quaternion.identity);
     
        
    }
}
