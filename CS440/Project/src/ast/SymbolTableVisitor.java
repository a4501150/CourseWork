package ast;

public class SymbolTableVisitor implements ssParserVisitor {
	
	public SymbolTableVisitor(){
		table=new SymbolTable();
	}
	public SymbolTable table;
	
	public Object visit(SimpleNode node, Object data) {
		throw new RuntimeException("not unimplemented in subclass?");
	}

	public Object visit(ASTGoal node, Object data) {
		node.childrenAccept(this, data);
		return null;
	}

	public Object visit(ASTMainClass node, Object data) {
		ASTIdentifier clazz=(ASTIdentifier) node.children[0];
		table.addClass(clazz.token.image);
		node.childrenAccept(this, data);
		return null;
	}

	public Object visit(ASTClassDeclaration node, Object data) {
		SymbolTable.ClassSymbolTable cst=table.addClass(Util.getClassName(node));
		if(node.children.length>1 && node.children[1] instanceof ASTIdentifier){
			ASTIdentifier c2=(ASTIdentifier) node.children[1];
			//table.addClass(c2.token.image);
			cst.parent=c2.token.image;
		}
		node.childrenAccept(this, data);
		return null;
	}
	
	public Object visit(ASTVarDeclaration node, Object data) {
		if(node.parent instanceof ASTClassDeclaration){
			ASTType type=(ASTType) node.children[0];
			ASTIdentifier i=(ASTIdentifier) node.children[1];
			ASTClassDeclaration c=(ASTClassDeclaration) node.parent;
			table.addField(i.token.image, type,Util.getClassName(c));
		}
		if(node.parent instanceof ASTMethodDeclaration){
			ASTType type=(ASTType) node.children[0];
			ASTIdentifier i=(ASTIdentifier) node.children[1];
			ASTMethodDeclaration m=(ASTMethodDeclaration) node.parent;
			ASTClassDeclaration c=(ASTClassDeclaration) m.parent;
			table.addLocal(i.token.image, type,Util.getMethodName(m),Util.getClassName(c));
		}	
		return null;
	}

	public Object visit(ASTMethodDeclaration node, Object data) {
		ASTClassDeclaration c=(ASTClassDeclaration) node.parent;
		ASTType t=(ASTType) node.children[0];
		table.addMethod(Util.getMethodName(node),t,Util.getClassName(c));
		int i=2;
		while(node.children.length>i+1){
			if(node.children[i] instanceof ASTType){
				if(node.children[i+1] instanceof ASTIdentifier){
					ASTType type=(ASTType) node.children[i];
					ASTIdentifier id=(ASTIdentifier) node.children[i+1];
					table.addParam(id.token.image, type,Util.getMethodName(node),Util.getClassName(c));
				}
			}
			i+=2;
		}
		node.childrenAccept(this, data);
		return null;
	}

	public Object visit(ASTReturnExpression node, Object data) {
		return null;
	}

	public Object visit(ASTType node, Object data) {
		return null;
	}

	public Object visit(ASTAssignment node, Object data) {
		return null;
	}

	public Object visit(ASTArrayAssignment node, Object data) {
		return null;
	}

	public Object visit(ASTAssignmentOperator node, Object data) {
		return null;
	}

	public Object visit(ASTIfStatement node, Object data) {
		return null;
	}

	public Object visit(ASTWhileStatement node, Object data) {
		return null;
	}

	public Object visit(ASTPrintStatement node, Object data) {
		return null;
	}

	public Object visit(ASTRelationalExpression node, Object data) {

		return null;
	}

	public Object visit(ASTRelationalOperator node, Object data) {

		return null;
	}

	public Object visit(ASTArrayExpression node, Object data) {

		return null;
	}

	public Object visit(ASTCallNode node, Object data) {

		return null;
	}

	public Object visit(ASTAddNode node, Object data) {

		return null;
	}

	public Object visit(ASTMultiNode node, Object data) {

		return null;
	}

	public Object visit(ASTLengthNode node, Object data) {

		return null;
	}

	public Object visit(ASTNewClassNode node, Object data) {

		return null;
	}

	public Object visit(ASTNewIntArrayNode node, Object data) {

		return null;
	}

	public Object visit(ASTNegNode node, Object data) {

		return null;
	}

	public Object visit(ASTThisNode node, Object data) {

		return null;
	}

	public Object visit(ASTIntConstNode node, Object data) {

		return null;
	}

	public Object visit(ASTTrueNode node, Object data) {

		return null;
	}

	public Object visit(ASTFalseNode node, Object data) {

		return null;
	}

	public Object visit(ASTExpList node, Object data) {

		return null;
	}

	public Object visit(ASTIdentifier node, Object data) {

		return null;
	}

	@Override
	public Object visit(ASTDoubleNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTCharNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTStringNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTEmptyStatement node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

}
