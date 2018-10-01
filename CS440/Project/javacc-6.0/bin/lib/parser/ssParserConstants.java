/* Generated By:JJTree&JavaCC: Do not edit this line. ssParserConstants.java */
package parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface ssParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int SINGLE_LINE_COMMENT = 9;
  /** RegularExpression Id. */
  int FORMAL_COMMENT = 10;
  /** RegularExpression Id. */
  int MULTI_LINE_COMMENT = 11;
  /** RegularExpression Id. */
  int INTEGER_LITERAL = 13;
  /** RegularExpression Id. */
  int DECIMAL_LITERAL = 14;
  /** RegularExpression Id. */
  int HEX_LITERAL = 15;
  /** RegularExpression Id. */
  int OCTAL_LITERAL = 16;
  /** RegularExpression Id. */
  int FLOATING_POINT_LITERAL = 17;
  /** RegularExpression Id. */
  int EXPONENT = 18;
  /** RegularExpression Id. */
  int CHARACTER_LITERAL = 19;
  /** RegularExpression Id. */
  int STRING_LITERAL = 20;
  /** RegularExpression Id. */
  int CLASS = 21;
  /** RegularExpression Id. */
  int PUBLIC = 22;
  /** RegularExpression Id. */
  int STATIC = 23;
  /** RegularExpression Id. */
  int VOID = 24;
  /** RegularExpression Id. */
  int STRING = 25;
  /** RegularExpression Id. */
  int EXTENDS = 26;
  /** RegularExpression Id. */
  int INT = 27;
  /** RegularExpression Id. */
  int DOUBLE = 28;
  /** RegularExpression Id. */
  int CHAR = 29;
  /** RegularExpression Id. */
  int BOOLEAN = 30;
  /** RegularExpression Id. */
  int IF = 31;
  /** RegularExpression Id. */
  int WHILE = 32;
  /** RegularExpression Id. */
  int ELSE = 33;
  /** RegularExpression Id. */
  int TRUE = 34;
  /** RegularExpression Id. */
  int FALSE = 35;
  /** RegularExpression Id. */
  int NEW = 36;
  /** RegularExpression Id. */
  int THIS = 37;
  /** RegularExpression Id. */
  int RETURN = 38;
  /** RegularExpression Id. */
  int MAIN = 39;
  /** RegularExpression Id. */
  int LENGTH = 40;
  /** RegularExpression Id. */
  int IDENTIFIER = 41;
  /** RegularExpression Id. */
  int LETTER = 42;
  /** RegularExpression Id. */
  int DIGIT = 43;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int IN_SINGLE_LINE_COMMENT = 1;
  /** Lexical state. */
  int IN_FORMAL_COMMENT = 2;
  /** Lexical state. */
  int IN_MULTI_LINE_COMMENT = 3;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "\"\\f\"",
    "\"//\"",
    "<token of kind 7>",
    "\"/*\"",
    "<SINGLE_LINE_COMMENT>",
    "\"*/\"",
    "\"*/\"",
    "<token of kind 12>",
    "<INTEGER_LITERAL>",
    "<DECIMAL_LITERAL>",
    "<HEX_LITERAL>",
    "<OCTAL_LITERAL>",
    "<FLOATING_POINT_LITERAL>",
    "<EXPONENT>",
    "<CHARACTER_LITERAL>",
    "<STRING_LITERAL>",
    "\"class\"",
    "\"public\"",
    "\"static\"",
    "\"void\"",
    "\"String\"",
    "\"extends\"",
    "\"int\"",
    "\"double\"",
    "\"char\"",
    "\"boolean\"",
    "\"if\"",
    "\"while\"",
    "\"else\"",
    "\"true\"",
    "\"false\"",
    "\"new\"",
    "\"this\"",
    "\"return\"",
    "\"main\"",
    "\"length\"",
    "<IDENTIFIER>",
    "<LETTER>",
    "<DIGIT>",
    "\"{\"",
    "\"(\"",
    "\"[\"",
    "\"]\"",
    "\")\"",
    "\"}\"",
    "\";\"",
    "\",\"",
    "\"=\"",
    "\"*=\"",
    "\"/=\"",
    "\"%=\"",
    "\"+=\"",
    "\"-=\"",
    "\"<<=\"",
    "\">>=\"",
    "\">>>=\"",
    "\"&=\"",
    "\"^=\"",
    "\"|=\"",
    "\"System.out.println\"",
    "\"<\"",
    "\">\"",
    "\"<=\"",
    "\">=\"",
    "\"==\"",
    "\"!=\"",
    "\".\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"%\"",
    "\"!\"",
  };

}