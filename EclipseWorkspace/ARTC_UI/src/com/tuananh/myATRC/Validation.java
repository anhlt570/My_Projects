package com.tuananh.myATRC;

import com.microsoft.z3.*;
import java.util.*;

public class Validation 
{
	@SuppressWarnings("serial")
    class TestFailedException extends Exception
    {
        public TestFailedException()
        {
            super("Check FAILED");
        }
    };
    
   
	public static void main(String [] args)
	{
		HashMap<String,String> cfg= new HashMap<String, String>();
		cfg.put("model", "true");
		Context ctx= new Context(cfg);
		
		
	}
}
