/* Generated By:JJTree: Do not edit this line. ASTRelationalOperator.java Version 6.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=MyNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ast;
import parser.ssParser;
public
class ASTRelationalOperator extends SimpleNode {
  public ASTRelationalOperator(int id) {
    super(id);
  }

  public ASTRelationalOperator(ssParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ssParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=a1a58913e4f002fbe2460988a3029064 (do not edit this line) */
