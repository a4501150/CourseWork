package ast;
import parser.*;
import java.util.Map.Entry;

import exception.MismatchException;
import exception.NotDefineException;
import parser.*;


public class TypeCheckVisitor implements ssParserVisitor {

	public TypeCheckVisitor(SymbolTable t){
		table=t;
	}
	public SymbolTable table;
	
	public Object visit(SimpleNode node, Object data) {
		throw new RuntimeException("not unimplemented in subclass?");
	}

	public Object visit(ASTGoal node, Object data) {
		node.childrenAccept(this, data);
		return data;
	}

	public Object visit(ASTMainClass node, Object data) {
		node.childrenAccept(this, data);
		return data;
	}

	public Object visit(ASTClassDeclaration node, Object data) {
		if(node.children.length>1 && node.children[1] instanceof ASTIdentifier){
			String clazz=Util.getClassName(node);
			ASTIdentifier c2=(ASTIdentifier) node.children[1];
			String parentClazz=c2.token.image;
			SymbolTable.ClassSymbolTable cst=table.classes.get(parentClazz);
			do{
				if(clazz.equals(parentClazz)) throw new RuntimeException("circular inherit:"+clazz);
				if(cst==null) throw new NotDefineException(parentClazz+" not defined.");
				if(cst.parent==null) break;
				cst=table.classes.get(cst.parent);
				parentClazz=cst.parent;
			}while(true);
		}
		node.childrenAccept(this, data);
		return data;
	}

	public Object visit(ASTVarDeclaration node, Object data) {
		
		
		//node.dump("ASTVarDeclaration");
		if(node.children.length<3)
			return node.children[0];
		
		SimpleNode a = (SimpleNode)node.children[0];
		SimpleNode b = (SimpleNode)node.children[3];
		
		//ASTIdentifier ai = (ASTIdentifier) node.children[0];
		//ASTType at = getType(ai);
		
		Object bo = b.jjtAccept(this, data);
		
		if(bo instanceof ASTIdentifier) {
			
			ASTType bt = getType(bo); 
			Util.typeEqual(a,(SimpleNode)bt);
			
		} else {
			Util.typeEqual(a,(SimpleNode)bo);
		}
		
		return node.children[0];
		
		
	}

	public Object visit(ASTMethodDeclaration node, Object data) {
		node.childrenAccept(this, data);
		return node.children[0];
	}

	public Object visit(ASTReturnExpression node, Object data) {
		if(node.children==null || node.children.length==0){
			ASTMethodDeclaration m=(ASTMethodDeclaration) node.parent;
			ASTType t=(ASTType) m.children[0];
			Util.typeEqual(t, "void");
			return voidType();//void
		}else{
			SimpleNode n=(SimpleNode) node.children[0];
			Object t= n.jjtAccept(this, data);
			ASTMethodDeclaration m=(ASTMethodDeclaration) node.parent;
			ASTType mt=(ASTType) m.children[0];
			Util.typeEqual(getType(t), mt);
			return t;
		}
	}
	
	public Object visit(ASTType node, Object data) {
		return node;
	}

	public Object visit(ASTAssignment node, Object data) {
		
			
		SimpleNode n=(SimpleNode) node.children[2];	
		Object t= n.jjtAccept(this, data);
		
		if(t instanceof ASTNewIntArrayNode) {
			Util.typeEqual(  getType(((SimpleNode)t).children[0]),"int");
			return t;
		}
		
		if(t instanceof ASTLengthNode) {
			return t;
		}
		
		ASTIdentifier i=(ASTIdentifier) node.children[0];
		ASTType t1=getType(i);
//		System.out.println(t1.tokenImage());
//		System.out.println(getType(t).tokenImage());
		
		if(t1.tokenImage().equalsIgnoreCase("int[]")) {
			if(!getType(t).tokenImage().equalsIgnoreCase("int[]"))
				throw new MismatchException("type mismatch: int[] <-> "+ getType(t).tokenImage());
		}
		
		Util.typeEqual(t1,getType(t));
		return t;		
		
	}

	public Object visit(ASTArrayAssignment node, Object data) {
		SimpleNode n=(SimpleNode) node.children[3];
		Object t= n.jjtAccept(this, data);
		ASTType t1=getType(t);
		
		if(!(t1.tokenImage().equalsIgnoreCase("int[]")||t1.tokenImage().equalsIgnoreCase("int"))) {
				throw new MismatchException("type mismatch: int[] <-> "+ getType(t).tokenImage());
		}
		
		return t;
	}

	public Object visit(ASTAssignmentOperator node, Object data) {
		return data;
	}

	public Object visit(ASTIfStatement node, Object data) {
		SimpleNode n=(SimpleNode) node.children[0];
		Object t=n.jjtAccept(this, data);
		Util.typeEqual(getType(t), "boolean");
		for(int i=1;i<node.children.length;i++)
			node.children[i].jjtAccept(this, data);
		return null;
	}

	public Object visit(ASTWhileStatement node, Object data) {
		SimpleNode n=(SimpleNode) node.children[0];
		Object t=n.jjtAccept(this, data);
		Util.typeEqual(getType(t), "boolean");
		node.children[1].jjtAccept(this, data);
		return null;
	}

	public Object visit(ASTPrintStatement node, Object data) {
		return null;
	}

	public Object visit(ASTRelationalExpression node, Object data) {
		SimpleNode n1=(SimpleNode) node.children[0];
		Object t1=n1.jjtAccept(this, data);
		Util.typeEqual(getType(t1),"int");
		SimpleNode n2=(SimpleNode) node.children[2];
		Object t2=n2.jjtAccept(this, data);
		Util.typeEqual(getType(t2),"int");
		return booleanType();
	}

	public Object visit(ASTRelationalOperator node, Object data) {
		return null;
	}

	public Object visit(ASTArrayExpression node, Object data) {
		Object ret=node.children[0].jjtAccept(this, data);
		node.children[1].jjtAccept(this, data);
		return ret;
	}

	public Object visit(ASTCallNode node, Object data) {
		System.out.println("******"+node.children[0]);
		ASTType clazz=(ASTType)node.children[0].jjtAccept(this, data);
		System.out.println("******"+clazz);	
		System.out.println("******"+Util.typeString(clazz));	
		ASTIdentifier method=(ASTIdentifier) node.children[1];
		ASTExpList expList=(ASTExpList) node.children[2];
		SymbolTable.MethodSymbolTable mt=table.findMethod(Util.typeString(clazz),method.tokenImage());
		if(mt.params.size()==0){
			if(expList.children!=null && expList.children.length>0){
				 throw new MismatchException("method "+method.tokenImage()+" argument number mismatch");
			}
		}else{
			if(expList.children==null || mt.params.size()!=expList.children.length) throw new MismatchException("method "+method.tokenImage()+" argument number mismatch");
			int i=0;
			for(Entry<String, ASTType> param : mt.params.entrySet()){
				Node exp=expList.children[i++];
				Object real=exp.jjtAccept(this, data);
				ASTType formula=param.getValue();
				Util.typeEqual(getType(real), formula);
			}
		}
		return mt.returnType;
	}
	


	public Object visit(ASTAddNode node, Object data) {
		SimpleNode n1=(SimpleNode) node.children[0];
		Object t1=n1.jjtAccept(this, data);
		//Util.typeEqual(getType(t1),"int");
		SimpleNode n2=(SimpleNode) node.children[1];
		Object t2=n2.jjtAccept(this, data);
		Util.typeEqual(getType(t2),getType(t1));
		return t1;
	}

	public Object visit(ASTMultiNode node, Object data) {
		SimpleNode n1=(SimpleNode) node.children[0];
		Object t1=n1.jjtAccept(this, data);
		Util.typeEqual(getType(t1),"int");
		SimpleNode n2=(SimpleNode) node.children[1];
		Object t2=n2.jjtAccept(this, data);
		Util.typeEqual(getType(t2),"int");
		return t1;
	}

	public Object visit(ASTLengthNode node, Object data) {
		// TODO Auto-generated method stub
		return node;
	}

	public Object visit(ASTNewClassNode node, Object data) {
		SimpleNode n1=(SimpleNode) node.children[0];
		SymbolTable.ClassSymbolTable clazz= table.findClass(n1.token.image);
		return n1;
	}

	public Object visit(ASTNewIntArrayNode node, Object data) {
		
		return node;
	}

	public Object visit(ASTNegNode node, Object data) {
		SimpleNode n1=(SimpleNode) node.children[0];
		ASTType t1=(ASTType) n1.jjtAccept(this, data);
		Util.typeEqual(t1, "boolean");
		return t1;
	}

	public Object visit(ASTThisNode node, Object data) {
		ASTMethodDeclaration m = getEncloseingMethod(node);
		ASTClassDeclaration clazz = (ASTClassDeclaration) m.parent;
		return objectType(((ASTIdentifier)clazz.children[0]).tokenImage());
	}

	public Object visit(ASTIntConstNode node, Object data) {
		ASTType i=new ASTType(Integer.parseInt(node.token.image));
		i.token=new Token();
		i.token.image="int";
		return i;
	}

	public Object visit(ASTTrueNode node, Object data) {
		return booleanType();
	}

	public Object visit(ASTFalseNode node, Object data) {
		return booleanType();
	}



	public Object visit(ASTExpList node, Object data) {
		if(node.children.length==0){
			ASTType i=new ASTType(-1);
			i.token=new Token();
			i.token.image="void";
			return i;
		}
        for (int i = 1; i < node.children.length; ++i) {
	          node.children[i].jjtAccept(this, data);
	        }
		return node.children[0].jjtAccept(this, data);
	}

	public Object visit(ASTIdentifier node, Object data) {
		return node;
	}
	
	private ASTType getType(Object o){
		if(o instanceof ASTType) return (ASTType) o;
		if(o instanceof ASTIdentifier){
			ASTIdentifier i=(ASTIdentifier) o;
			ASTType t1 = getType(i);
			return t1;
		}
		
		throw new RuntimeException("impossible.");
	}

	private ASTType getType(ASTIdentifier i) {
		try{
			table.findClass(i.tokenImage());
			return objectType(i.tokenImage());
		}catch(NotDefineException e){}
		ASTMethodDeclaration m = getEncloseingMethod(i);
		ASTClassDeclaration clazz = (ASTClassDeclaration) m.parent;
		ASTType t1 = table.findLocalOrParam(i.tokenImage(), Util.getMethodName(m),
				Util.getClassName(clazz));
		return t1;
	}
	
	private ASTMethodDeclaration getEncloseingMethod(SimpleNode i){
		SimpleNode p = (SimpleNode) i.parent;
		while (!(p instanceof ASTMethodDeclaration)) {
			p = (SimpleNode) p.parent;
		}
		return (ASTMethodDeclaration) p;
	}
	
	private ASTType objectType(String name){
		ASTType i=new ASTType(0);
		i.token=new Token();
		i.token.image=name;
		return i;
	}

	private ASTType booleanType() {
		ASTType i=new ASTType(0);
		i.token=new Token();
		i.token.image="boolean";
		return i;
	}
	
	private ASTType voidType() {
		ASTType i=new ASTType(0);
		i.token=new Token();
		i.token.image="void";
		return i;
	}

	@Override
	public Object visit(ASTDoubleNode node, Object data) {
		ASTType i=new ASTType(Double.parseDouble(node.token.image));
		i.token=new Token();
		i.token.image="double";
		return i;
	}

	@Override
	public Object visit(ASTCharNode node, Object data) {
		ASTType i=new ASTType((node.token.image).charAt(0));
		i.token=new Token();
		i.token.image="char";
		return i;
	}

	@Override
	public Object visit(ASTStringNode node, Object data) {
		ASTType i=new ASTType(node.token.image);
		i.token=new Token();
		i.token.image="string";
		return i;
	}

	@Override
	public Object visit(ASTEmptyStatement node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
