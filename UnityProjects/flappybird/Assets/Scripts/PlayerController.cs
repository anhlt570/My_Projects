using UnityEngine;
using System.Collections;

public class PlayerController : MonoBehaviour {
    
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	    if(Input.GetKeyDown(KeyCode.Space))
        {
            transform.Translate(Vector3.right * 5.0f);
        }
	}
}
