package com.tuananh.myATRC;

import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Z3Exception;

public class ExpressionTree
{
	Reader r= new Reader();
	public Expression root;
	
	public ExpressionTree()
	{
		root=null;
	}
	
	public ExpressionTree (Expression exp)
	{
		this.root= exp;
	}
	
	public ExpressionTree(String text)
	{
		text= r.standardizedText(text);
		
	}
	
	
	public Expr generateExpression(Context ctx, Expression exp) throws Z3Exception
	{
		if(exp.leftExp.isParam()&&exp.rightExp.isParam())
		{
			String tmp =exp.leftExp.data+exp.data+exp.rightExp.data;
			
			if(r.isArith(exp.data.charAt(0))) return r.generateArithExpression(ctx, tmp);
			if(r.isBoolean(exp.data.charAt(0))) return r.generateBoolExpression(ctx, tmp);
		}
		else
		{
			return r.generateBoolExpression(ctx, exp.data, generateExpression(ctx, exp.leftExp),generateExpression(ctx, exp.rightExp));
		}
		return null;
	}
	public Expr generateExpression(Context ctx) throws Z3Exception
	{
		return generateExpression(ctx,this.root);
	}
	
}
