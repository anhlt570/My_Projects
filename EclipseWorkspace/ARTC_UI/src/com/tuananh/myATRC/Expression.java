package com.tuananh.myATRC;

import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Z3Exception;

public class Expression
{
	public String data;
	public int dataType;
	public Expression fatherExp;
	public Expression leftExp;
	public Expression rightExp;
	
	public Expression()
	{
		this.data=null;
		dataType=-1;
		fatherExp=null;
		leftExp=rightExp=null;
	}
	
	public Expression(String data, int dataType)
	{
		fatherExp=null;
		this.data= data;
		this.dataType = dataType;
		leftExp=rightExp=null;
	}
	
	public boolean isParam()
	{
		return (this.leftExp==null||this.rightExp==null);
	}
	
}
