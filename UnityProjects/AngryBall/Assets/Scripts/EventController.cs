using UnityEngine;
using System.Collections;

public class EventController : MonoBehaviour {
   public
	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}

    public void LoadGame(int index)
    {
        Application.LoadLevel(index);
    }

   public void exit()
    {
        Application.Quit();
    }
}
