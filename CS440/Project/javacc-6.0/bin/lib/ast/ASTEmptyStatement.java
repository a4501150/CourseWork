/* Generated By:JJTree: Do not edit this line. ASTEmptyStatement.java Version 6.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=MyNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ast;

public
class ASTEmptyStatement extends SimpleNode {
  public ASTEmptyStatement(int id) {
    super(id);
  }

  public ASTEmptyStatement(ssParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ssParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=74fb4a647e01c57472a4de63f9152249 (do not edit this line) */
