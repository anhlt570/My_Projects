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
	public String[] operators;
	public int[] operands;
	public int[] priority;
	public Expr[] variables;

	public Reader()
	{
		operators = null;
		operands = null;
		priority = null;
		variables = null;
	}
	// public String readFile(String fileName) throws IOException
	// {
	// FileInputStream inFile = null;
	// String tmp = null;
	// try
	// {
	// inFile = new FileInputStream(fileName);
	// int c;
	// while ((c = inFile.read()) != -1)
	// {
	// tmp += (char) c;
	// }
	// }
	// catch (IOException e)
	// {
	// e.printStackTrace();
	// }
	// return tmp;
	// }

	public void setPriority(String expression)
	{
		priority = new int[operators.length];
		int p = 1;
		int r = 0;
		for (int i = 0; i < expression.length(); i++)
		{
			if (expression.charAt(i) == '(')
				p++;
			else if (expression.charAt(i) == ')')
				p--;
			else if (isOpertor(expression.charAt(i)))
			{
				priority[r] = p;
				r++;
				if (expression.charAt(i) == '[')
				{
					if (expression.charAt(i + 1) == 'a' || expression.charAt(i + 1) == 'x')
						i += 4;
					else if (expression.charAt(i + 1) == 'o')
						i += 3;
				}
				else if (expression.charAt(i) == '=')
				{
					if (expression.charAt(i + 1) == '>')
						i++;
				}
				else if (expression.charAt(i) == '>' || expression.charAt(i) == '<')
				{
					if (expression.charAt(i + 1) == '=')
						i++;
				}
			}
		}
	}

	public boolean isNumber(String text/* , int number */)
	{
		for (int i = 0; i < text.length(); i++)
		{
			if (text.charAt(i) < '0' || text.charAt(i) > '9')
				return false;
		}
		// number = Integer.parseInt(text);
		return true;
	}

	public void sortStrings(String[] strs)
	{
		boolean check = false;
		while (!check)
		{
			check = true;
			for (int i = 0; i < strs.length - 1; i++)
			{
				if (strs[i].length() < strs[i + 1].length())
				{
					String tmp = strs[i];
					strs[i] = strs[i + 1];
					strs[i + 1] = tmp;
					check = false;
				}
			}
		}
	}

	public String analyzeText(String text) throws Z3Exception
	{
		// Context ctx= new Context();
		String tmp = standardizedText(text);
		String tmp1 = tmp;
		tmp = tmp.replaceAll("[+-/><()*=]", " ");
		tmp = tmp.replaceAll("and|or|xor", " ");
		tmp = tmp.replaceAll("\\[", "");
		tmp = tmp.replaceAll("]", "");
		while (tmp.contains("  "))
		{
			tmp = tmp.replaceAll("  ", " ");
		}

		String[] tmpOperands = tmp.split(" ");
		String[] tmpVariables = new String[tmpOperands.length];
		int tmpI = 0;
		for (int i = 0; i < tmpOperands.length; i++)
		{
			boolean isExist = false;
			if (tmpVariables != null)
				for (int j = 0; j < tmpVariables.length; j++)
				{
					if (tmpVariables[j] == tmpOperands[i])
					{
						isExist = true;
						break;
					}
				}
			if (!isExist)
			{
				tmpVariables[tmpI] = new String(tmpOperands[i]);
				tmpI++;
			}
		}
		Context ctx = new Context();
		sortStrings(tmpVariables);
		variables = new Expr[tmpVariables.length];
		operands = new int[tmpOperands.length];
		for (int i = 0; i < tmpOperands.length; i++)
		{
			for (int j = 0; j < tmpVariables.length; j++)
			{
				if (tmpVariables[j] == tmpOperands[i])
				{
					operands[i] = j;
					break;
				}
				
			}
		}
		for (int i = 0; i < tmpVariables.length; i++)
		{
			variables[i] = ctx.mkIntConst(tmpVariables[i]);
		}
//		String check = null;
//		for (int i = 0; i < operands.length; i++)
//		{
//			check += operands[i] + "\n";
//		}

		for (int i = 0; i < tmpVariables.length; i++)
		{
			tmp1 = tmp1.replaceAll(tmpVariables[i], "");
		}

		String strOperators = tmp1;
		strOperators = strOperators.replaceAll("[()]", " ");
		while (strOperators.contains("  "))
		{
			strOperators = strOperators.replaceAll("  ", " ");
		}
		operators = strOperators.split(" ");
		setPriority(tmp1);
		String testOperators = null;
		for (int i = 0; i < priority.length; i++)
		{
			System.out.print(priority[i] + "^");
		}
		System.out.println(tmpVariables.length-operators.length);
		return tmp1;
	}

	public Expr generateExpr(Context ctx, String operator, Expr e1, Expr e2) throws Z3Exception
	{
		if (isBoolean(operator))
			return generateBoolExpression(ctx, operator, e1, e2);
		else if (isArith(operator))
			return generateArithExpression(ctx, operator, (ArithExpr) e1, (ArithExpr) e2);
		return null;
	}

	public Expr generateExprFromString(Context ctx,String text) throws Z3Exception
	{
		analyzeText(text);
		Expr[] saveExpr =new Expr[priority.length];
		BoolExpr p = null;
		int maxPriority = 1;
		for (int i = 0; i < priority.length; i++)
		{
			if (priority[i] > maxPriority)
				maxPriority = priority[i];
		}
		int maxPriority1 =maxPriority;
		for(int i=0;i<saveExpr.length;i++)
		{
			saveExpr[i]= variables[operands[i]];
		}
		while (maxPriority1 >= 1)
		{
			for (int i = 0; i < priority.length; i++)
			{
				if(priority[i]==maxPriority1)
				{
					Expr left = null;
					Expr right = null;
					//int leftPos,rightPos;
					for(int j=i;j>=0;j--)
					{
						if(saveExpr[j]!=null)
						{
						//	leftPos=j;
							left= saveExpr[j];
							saveExpr[j+1]=null;
							System.out.print(String.valueOf(j));
							break;
						}
					}
					for(int j=i+1;j<priority.length;j++)
					{
						if(saveExpr[j]!=null)
						{
							//rightPos =j;
							right= saveExpr[j];
							saveExpr[j+1]=null;
							System.out.print("+"+String.valueOf(j));
							break;
						}
					}
					System.out.print("/");
					saveExpr[i]=generateExpr(ctx, operators[i], left, right);
				}
			}
			System.out.print("\n______\n");
			maxPriority1--;
		}
		return p;
	}

	public ExpressionTree generateTree(String text)
	{
		ExpressionTree myTree = null;
		String tmp = standardizedText(text);

		return myTree;
	}

	public boolean isOpertor(char c)
	{
		char[] ops = { '+', '-', '*', '/', '=', '[', '<', '>' };
		for (int i = 0; i < ops.length; i++)
		{
			if (c == ops[i])
				return true;
		}
		return false;
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

	// public BoolExpr generateBoolExpression(Context ctx, String text) throws
	// Z3Exception
	// {
	// String firstParam = null, seccondParam = null;
	// char c = 0;
	// boolean checkExpand = false;
	// for (int i = 0; i < text.length(); i++)
	// {
	// c = text.charAt(i);
	// if (isBoolean(c))
	// {
	// if (c == '<' || c == '>')
	// {
	// if (text.charAt(i + 1) == '=')
	// {
	// checkExpand = true;
	// firstParam = text.substring(0, i - 1);
	// seccondParam = text.substring(i + 2, text.length() - 1);
	// }
	// else
	// {
	// firstParam = text.substring(0, i - 1);
	// seccondParam = text.substring(i + 1, text.length() - 1);
	// }
	// }
	// }
	// }
	// IntExpr a = ctx.mkIntConst(firstParam);
	// IntExpr b = ctx.mkIntConst(seccondParam);
	// switch (c)
	// {
	// case '=':
	// return ctx.mkEq(a, b);
	// case '<':
	// {
	// if (checkExpand)
	// return ctx.mkOr(ctx.mkEq(a, b), ctx.mkLt(a, b));
	// else
	// return ctx.mkLt(a, b);
	// }
	// case '>':
	// {
	// if (checkExpand)
	// return ctx.mkOr(ctx.mkEq(a, b), ctx.mkGt(a, b));
	// else
	// return ctx.mkGt(a, b);
	// }
	//
	// default:
	// break;
	// }
	// return null;
	// }

	// public int numberOfExpressions(String text)
	// {
	// String tmp = null;
	// for (int i = 0; i < text.length(); i++)
	// {
	// if (text.charAt(i) == '(' || text.charAt(i) == ')')
	// {
	// tmp += text.charAt(i);
	// }
	// }
	// int num = 0;
	// for (int i = 0; i < tmp.length(); i++)
	// {
	// if (tmp.charAt(i) == '(' && tmp.charAt(i + 1) == ')')
	// num++;
	// }
	// return num;
	// }

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
		String tmp = text.replaceAll("endif", "");
		tmp = tmp.replaceAll("if", "(");
		tmp = tmp.replaceAll("then", "=>");
		if (!tmp.contains("else") && tmp.contains("=>"))
			tmp += ")";
		else if (tmp.contains("else"))
		{
			tmp = tmp.replaceAll("else if", ")[and](");
			tmp = tmp.replaceAll("else", ")[and]");

		}
		return tmp;
	}

	public String standardizedText(String text)
	{
		text = text.replaceAll("AND|and", "[and]");
		text = standardizedConditionString(text);
		text = text.replaceAll("OR|or", "[or]");
		text = text.replaceAll("XOR|xor", "[xor]");
		text = text.replaceAll("\n|\r|\t", " ");
		text = text.replaceAll(" ", "");

		return text;
	}

}
