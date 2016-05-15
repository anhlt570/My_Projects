package com.tuananh.myATRC;


import java.awt.List;
import java.beans.Expression;
import java.io.*;
import java.util.*;
import com.microsoft.z3.*;
import com.microsoft.z3.Expr;

public class Reader 
{
	public String readFile(String fileName) throws IOException
	{
		FileInputStream inFile = null;
		String tmp=null;
		try
		{
			inFile= new FileInputStream(fileName);
			int c;
			while ((c=inFile.read())!=-1)
			{
				tmp+= (char) c;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return tmp;
	}
	
	public boolean isOpertor(char c)
	{
		if(c>'0'&&c<'9') return false;
		if(c>'a'&&c<'z') return false;
		if(c>'A'&& c<'Z') return false;
		return true;
	}
	
	public boolean isBoolean(char c)
	{
		char[] tmp={'<','=','>','#','&','|'};
		for (char d : tmp)
		{
			if(d==c) return true;
		}
		return false;
	}
	public boolean isArith(char c)
	{
		char []tmp={'+','-','*','/'};
		for (char d : tmp)
		{
			if(d==c) return true;
		}
		return false;
	}
	
	public ArithExpr generateArithExpression(Context ctx,String text) throws Z3Exception
	{
		String firstParam = null, seccondParam=null;
		char c = 0;
		for(int i=0;i<text.length();i++)
		{
			c= text.charAt(i);
			if (isArith(c))
			{
				firstParam= text.substring(0,i-1);
				seccondParam= text.substring(i+1,text.length()-1);
			}
		}
		IntExpr a= ctx.mkIntConst(firstParam);
		IntExpr b= ctx.mkIntConst(seccondParam);
		switch(c)
		{	
		case '+':
			return ctx.mkAdd(a,b);
		case '-':
			return ctx.mkSub(a,b);
		case '*':
			return ctx.mkMul(a,b);
		case '/':
			return ctx.mkDiv(a,b);
		default: break;
		}
		return null;
	}
	public Expr generateBoolExpression(Context ctx,String operator, Expr left, Expr right)throws Z3Exception
	{
		char c= operator.charAt(0);
		boolean checkExpand= (operator.length()>1&&operator.charAt(1)=='=');
		switch(c)
		{	
		case '=':
			return ctx.mkEq(left,right);
		case '<':
		{
			if(checkExpand)
				return ctx.mkOr(ctx.mkEq(left,right),ctx.mkLt((ArithExpr)left,(ArithExpr)right));
			else
				return ctx.mkLt((ArithExpr)left,(ArithExpr)right);
		}
		case '>':
		{
			if(checkExpand)
				return ctx.mkOr(ctx.mkEq(left,right),ctx.mkGt((ArithExpr)left,(ArithExpr)right));
			else
				return ctx.mkGt((ArithExpr)left,(ArithExpr)right);
		}
		
		default: break;
		}
		return null;
	}
	
	public BoolExpr generateBoolExpression(Context ctx,String text)throws Z3Exception
	{
		String firstParam = null, seccondParam=null;
		char c = 0;
		boolean checkExpand= false;
		for(int i=0;i<text.length();i++)
		{
			c= text.charAt(i);
			if (isBoolean(c))
			{
				if(c=='<'||c=='>')
				{
					if(text.charAt(i+1)=='=')
					{
						checkExpand= true;
						firstParam= text.substring(0,i-1);
						seccondParam= text.substring(i+2,text.length()-1);
					}
					else
					{
						firstParam= text.substring(0,i-1);
						seccondParam= text.substring(i+1,text.length()-1);
					}
				}
			}
		}
		IntExpr a= ctx.mkIntConst(firstParam);
		IntExpr b= ctx.mkIntConst(seccondParam);
		switch(c)
		{	
		case '=':
			return ctx.mkEq(a,b);
		case '<':
		{
			if(checkExpand)
				return ctx.mkOr(ctx.mkEq(a,b),ctx.mkLt(a,b));
			else
				return ctx.mkLt(a,b);
		}
		case '>':
		{
			if(checkExpand)
				return ctx.mkOr(ctx.mkEq(a,b),ctx.mkGt(a,b));
			else
				return ctx.mkGt(a,b);
		}
		
		default: break;
		}
		return null;
	}
	/*
	 * reading text like (expression)
	 * */
	public Expr readParenthesis(String text)
	{
		if(text.charAt(1)=='(')
		{
			String tmp = text.substring(1, text.length()-1);
			readParenthesis(tmp);
		}
		else
		{
			char c;
			for (int i=0;i<text.length();i++)
			{
				if(text.indexOf(i)==')')
				{
					
				}
			}
		}
		return null;
	}
	
	public String standardizedText(String text) 
	{
		text = text.replaceAll("AND|and", "&");
		text = text.replaceAll("OR|or", "|");
		text = text.replaceAll("endif", "");
		text =text.replaceAll("else", ")#");
		text = text.replaceAll("then", "&");
		text = text.replaceAll("if", "(");
		text= text.replaceAll("[ ]", "");
		text = text.replaceAll("\n","");
		
		return text;
	}
	
}
