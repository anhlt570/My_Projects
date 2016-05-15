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

	Reader tr = new Reader();


	public boolean checkInvarants(String Ini, String Evo)
	{
		String tmp1 = tr.standardizedText(Ini);
		String tmp2 = tr.standardizedText(Evo);
		Scanner scanner1 = new Scanner(tmp1);
		/*
		 * check the number of invarants in two string is equal or not
		 */
		String[] numLines1 = tmp1.split("\n");
		String[] numLines2 = tmp2.split("\n");
		if (numLines1.length != numLines2.length)
		{
			scanner1.close();
			return false;
		} else
		{
			System.out.println("numbers of lines is equal");
		}
		while (scanner1.hasNextLine())
		{
			String tmpLine = scanner1.nextLine();
			if (!tmp2.contains(tmpLine))
			{
				scanner1.close();
				return false;
			}
		}
		scanner1.close();
		return true;
	}
	
	public boolean checkPrecondition(String Initial, String Evolution)
	{
		return true;
	}

	public static void main(String[] args)
	{
		String tmp = "Helo! i am student";
		boolean b = tmp.contains("Am");
		if (b == true)
			System.out.println("holy shit");
		else
			System.out.println("it's ok");
	}

};
