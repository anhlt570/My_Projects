package com.tuananh.myATRC;

import java.awt.List;
import java.beans.Expression;
import java.io.*;
import java.lang.invoke.SwitchPoint;
import java.util.*;
import com.microsoft.z3.*;
import com.microsoft.z3.Expr;

public class Reader
{
	String[] operators;
	int[] priority;
	Expr[] variables;
	
//	public String readFile(String fileName) throws IOException
//	{
//		FileInputStream inFile = null;
//		String tmp = null;
//		try
//		{
//			inFile = new FileInputStream(fileName);
//			int c;
//			while ((c = inFile.read()) != -1)
//			{
//				tmp += (char) c;
//			}
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//		return tmp;
//	}
	
	public String analyzeText(String text)
	{
		String tmp =standardizedText(text);
		tmp = tmp.replaceAll("[+-/><()*=]"," ");
		tmp =tmp.replaceAll("and|or|xor", " ");
		tmp =tmp.replaceAll("][", "");
		while(tmp.contains("  "))
		{
			tmp=tmp.replaceAll("  "," ");
		}
		String[] listText = tmp.split(" ");
		String tmp1=null;
		for(int i=0;i<listText.length;i++)
		{
			tmp1+= listText[i]+" ";
		}
		return tmp1;
	}

	public Expr generateExpr(Context ctx, String operator, Expr e1, Expr e2) throws Z3Exception
	{
		if (isBoolean(operator))
			return generateBoolExpression(ctx, operator, e1, e2);
		else if(isArith(operator))
			return generateArithExpression(ctx, operator, (ArithExpr)e1, (ArithExpr)e2);
		return null;
	}

	public ExpressionTree generateTree(String text)
	{
		ExpressionTree myTree = null;
		String tmp = standardizedText(text);

		return myTree;
	}

	public boolean isOpertor(char c)
	{
		if (c > '0' && c < '9')
			return false;
		if (c > 'a' && c < 'z')
			return false;
		if (c > 'A' && c < 'Z')
			return false;
		return true;
	}

	public boolean isBoolean(String operator)
	{
		String[] booleanOperators = { "=", ">", ">=", "<", "<=", "=>", "[and]", "[or]", "[xor]" };
		for (String tmp : booleanOperators)
		{
			if (tmp == operator)
				return true;
		}
		return false;
	}

	public boolean isArith(char c)
	{
		char[] tmp = { '+', '-', '*', '/' };
		for (char d : tmp)
		{
			if (d == c)
				return true;
		}
		return false;
	}

	public boolean isArith(String operator)
	{
		String[] tmp = { "+", "-", "*", "/" };
		for (String st : tmp)
		{
			if (operator == st)
				return true;
		}
		return false;
	}

	public ArithExpr generateArithExpression(Context ctx, String operator, ArithExpr e1, ArithExpr e2)
			throws Z3Exception
	{
		switch (operator)
		{
		case "+":
			return ctx.mkAdd(e1, e2);
		case "-":
			return ctx.mkSub(e1, e2);
		case "*":
			return ctx.mkMul(e1, e2);
		case "/":
			return ctx.mkDiv(e1, e2);
		default:
			break;
		}
		return null;
	}

	public ArithExpr generateArithExpression(Context ctx, String text) throws Z3Exception
	{
		String firstParam = null, seccondParam = null;
		char c = 0;
		for (int i = 0; i < text.length(); i++)
		{
			c = text.charAt(i);
			if (isArith(c))
			{
				firstParam = text.substring(0, i - 1);
				seccondParam = text.substring(i + 1, text.length() - 1);
			}
		}
		IntExpr a = ctx.mkIntConst(firstParam);
		IntExpr b = ctx.mkIntConst(seccondParam);
		switch (c)
		{
		case '+':
			return ctx.mkAdd(a, b);
		case '-':
			return ctx.mkSub(a, b);
		case '*':
			return ctx.mkMul(a, b);
		case '/':
			return ctx.mkDiv(a, b);
		default:
			break;
		}
		return null;
	}

	public Expr generateBoolExpression(Context ctx, String operator, Expr left, Expr right) throws Z3Exception
	{
		char c = operator.charAt(0);
		boolean checkExpand = (operator.length() > 1 && operator.charAt(1) == '=');
		switch (c)
		{
		case '=':
			return ctx.mkEq(left, right);
		case '<':
		{
			if (checkExpand)
				return ctx.mkOr(ctx.mkEq(left, right), ctx.mkLt((ArithExpr) left, (ArithExpr) right));
			else
				return ctx.mkLt((ArithExpr) left, (ArithExpr) right);
		}
		case '>':
		{
			if (checkExpand)
				return ctx.mkOr(ctx.mkEq(left, right), ctx.mkGt((ArithExpr) left, (ArithExpr) right));
			else
				return ctx.mkGt((ArithExpr) left, (ArithExpr) right);
		}
		case '[':
		{
			if (operator == "[and]")
				return ctx.mkAnd((BoolExpr) left, (BoolExpr) right);
			else if (operator == "[or]")
				return ctx.mkOr((BoolExpr) left, (BoolExpr) right);
			else if (operator == "[xor]")
				return ctx.mkXor((BoolExpr) left, (BoolExpr) right);
		}
		default:
			break;
		}
		return null;
	}

//	public BoolExpr generateBoolExpression(Context ctx, String text) throws Z3Exception
//	{
//		String firstParam = null, seccondParam = null;
//		char c = 0;
//		boolean checkExpand = false;
//		for (int i = 0; i < text.length(); i++)
//		{
//			c = text.charAt(i);
//			if (isBoolean(c))
//			{
//				if (c == '<' || c == '>')
//				{
//					if (text.charAt(i + 1) == '=')
//					{
//						checkExpand = true;
//						firstParam = text.substring(0, i - 1);
//						seccondParam = text.substring(i + 2, text.length() - 1);
//					}
//					else
//					{
//						firstParam = text.substring(0, i - 1);
//						seccondParam = text.substring(i + 1, text.length() - 1);
//					}
//				}
//			}
//		}
//		IntExpr a = ctx.mkIntConst(firstParam);
//		IntExpr b = ctx.mkIntConst(seccondParam);
//		switch (c)
//		{
//		case '=':
//			return ctx.mkEq(a, b);
//		case '<':
//		{
//			if (checkExpand)
//				return ctx.mkOr(ctx.mkEq(a, b), ctx.mkLt(a, b));
//			else
//				return ctx.mkLt(a, b);
//		}
//		case '>':
//		{
//			if (checkExpand)
//				return ctx.mkOr(ctx.mkEq(a, b), ctx.mkGt(a, b));
//			else
//				return ctx.mkGt(a, b);
//		}
//
//		default:
//			break;
//		}
//		return null;
//	}

//	public int numberOfExpressions(String text)
//	{
//		String tmp = null;
//		for (int i = 0; i < text.length(); i++)
//		{
//			if (text.charAt(i) == '(' || text.charAt(i) == ')')
//			{
//				tmp += text.charAt(i);
//			}
//		}
//		int num = 0;
//		for (int i = 0; i < tmp.length(); i++)
//		{
//			if (tmp.charAt(i) == '(' && tmp.charAt(i + 1) == ')')
//				num++;
//		}
//		return num;
//	}

	/*
	 * reading text like (expression)
	 */

	// public ExpressionTree readParenthesis(String text)
	// {
	// ExpressionTree t = null;
	// ExpressionTree [] listTree=null;
	// String[] listExpression= null;
	// if(text.substring(0,1)=="((")
	// {
	// for (int i = 0; i < text.length() - 1; i++)
	// {
	// if (text.charAt(i) != text.charAt(i + 1))
	// {
	//
	// }
	// }
	// }
	// else
	// {
	// for(int i=0;i<text.length();i++)
	// {
	// if(isBoolean(text.charAt(i)))
	// {
	//
	// }
	// }
	// }
	// return t;
	// }

	public String standardizedConditionString(String text)
	{
		
		String tmp= text.replaceAll("endif", "");
		tmp = tmp.replaceAll("if", "(");
		tmp= tmp.replaceAll("then", "=>");
		if(!tmp.contains("else")) tmp+=")";
		else
		{
			tmp=tmp.replaceAll("else",")"+"[and]"+"(");
			
		}
		return tmp;
	}
	public String standardizedText(String text)
	{
		text = text.replaceAll("AND|and", "[and]");
		text= standardizedConditionString(text);
		text = text.replaceAll("OR|or", "[or]");
		text = text.replaceAll("XOR|xor", "[xor]");
		text = text.replaceAll("[\n]", "");
		
		return text;
	}

}
