package ast;
public class ssASTPrintVisitor implements ssParserVisitor {

	private int indent = 0;

	private String indentString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < indent; ++i) {
			sb.append(".");
		}
		return sb.toString();
	}
	
	private Object visit0(SimpleNode node, Object data) {
		System.out.println(indentString() + node + " : "+node.tokenImage());
		++indent;
		data = node.childrenAccept(this, data);
		--indent;
		return data;
	}

	public Object visit(SimpleNode node, Object data) {
		System.out.println(indentString() + node + ": acceptor not implemented in subclass");
		return visit0(node, data);
	}



	public Object visit(ASTGoal node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTMainClass node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTClassDeclaration node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTVarDeclaration node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTMethodDeclaration node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTReturnExpression node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTType node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTAssignment node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTArrayAssignment node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTAssignmentOperator node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTIfStatement node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTWhileStatement node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTPrintStatement node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTRelationalExpression node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTRelationalOperator node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTArrayExpression node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTCallNode node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTAddNode node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTMultiNode node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTLengthNode node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTNewClassNode node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTNewIntArrayNode node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTNegNode node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTThisNode node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTIntConstNode node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTTrueNode node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTFalseNode node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTExpList node, Object data) {
		return visit0(node, data);
	}

	public Object visit(ASTIdentifier node, Object data) {
		return visit0(node, data);
	}

	@Override
	public Object visit(ASTDoubleNode node, Object data) {
		return visit0(node, data);
	}

	@Override
	public Object visit(ASTCharNode node, Object data) {
		return visit0(node, data);
	}

	@Override
	public Object visit(ASTStringNode node, Object data) {
		return visit0(node, data);
	}

	@Override
	public Object visit(ASTEmptyStatement node, Object data) {
		return visit0(node, data);
	}

}
