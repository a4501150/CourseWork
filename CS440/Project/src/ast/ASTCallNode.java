/* Generated By:JJTree: Do not edit this line. ASTCallNode.java Version 6.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=MyNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ast;
import parser.ssParser;
public
class ASTCallNode extends SimpleNode {
  public ASTCallNode(int id) {
    super(id);
  }

  public ASTCallNode(ssParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ssParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=b84a05da940a0f695e7591a1e8a7aaff (do not edit this line) */
