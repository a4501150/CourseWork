/* Generated By:JJTree: Do not edit this line. ASTMultiNode.java Version 6.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=MyNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ast;

public
class ASTMultiNode extends SimpleNode {
  public ASTMultiNode(int id) {
    super(id);
  }

  public ASTMultiNode(ssParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ssParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=ae26bc3e758b9762fc51d096c75da5cc (do not edit this line) */