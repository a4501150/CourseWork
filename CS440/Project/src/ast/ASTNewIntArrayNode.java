/* Generated By:JJTree: Do not edit this line. ASTNewIntArrayNode.java Version 6.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=MyNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ast;
import parser.ssParser;
public
class ASTNewIntArrayNode extends SimpleNode {
  public ASTNewIntArrayNode(int id) {
    super(id);
  }

  public ASTNewIntArrayNode(ssParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ssParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=b9aeeeac0c4c3de0652ceed4ff4c1c61 (do not edit this line) */
