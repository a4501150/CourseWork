/* Generated By:JJTree: Do not edit this line. ASTRelationalExpression.java Version 6.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=MyNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ast;
import parser.ssParser;
public
class ASTRelationalExpression extends SimpleNode {
  public ASTRelationalExpression(int id) {
    super(id);
  }

  public ASTRelationalExpression(ssParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ssParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=3845a255e07b11c204068b3141b7fc47 (do not edit this line) */
