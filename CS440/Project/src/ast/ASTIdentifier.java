/* Generated By:JJTree: Do not edit this line. ASTIdentifier.java Version 6.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=MyNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ast;
import parser.ssParser;
public
class ASTIdentifier extends SimpleNode {
  public ASTIdentifier(int id) {
    super(id);
  }

  public ASTIdentifier(ssParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ssParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=867ffc05729b0de04385562c5b7eb46d (do not edit this line) */