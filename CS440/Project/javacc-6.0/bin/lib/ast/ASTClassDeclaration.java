/* Generated By:JJTree: Do not edit this line. ASTClassDeclaration.java Version 6.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=MyNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ast;

public
class ASTClassDeclaration extends SimpleNode {
  public ASTClassDeclaration(int id) {
    super(id);
  }

  public ASTClassDeclaration(ssParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ssParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=be0d9259931d7aa8d077413de51bb6a8 (do not edit this line) */
