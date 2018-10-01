package ast;
import exception.MismatchException;
import parser.*;

public class Util {
	private Util(){};

	public static String getClassName(ASTClassDeclaration c){
		ASTIdentifier clazz=(ASTIdentifier) c.children[0];
		return clazz.token.image;
	}

	public static String getMethodName(ASTMethodDeclaration m){
		ASTIdentifier clazz=(ASTIdentifier) m.children[1];
		return clazz.token.image;
	}

	public static String typeString(ASTType t1){
		String s1=null;
		if(t1.token!=null) s1=t1.token.image;
		if(s1==null) s1=((ASTIdentifier)t1.children[0]).tokenImage();
		return s1;
	}
	
	public static String typeString(SimpleNode t1){
		String s1=null;
		if(t1.token!=null) s1=t1.token.image;
		if(s1==null) s1=((ASTIdentifier)t1.children[0]).tokenImage();
		
		
		return s1;
	}

	public static void typeEqual(ASTType t1,ASTType t2){
		String s1=typeString(t1);
		String s2=typeString(t2);
		if(s1.equalsIgnoreCase("int[]")||s2.equalsIgnoreCase("int[]"))
			s1 = s2;
		if(!s2.equals(s1)) throw new MismatchException("type mismatch: "+s2+" <-> "+s1);
	}

	public static void typeEqual(SimpleNode t1,SimpleNode t2){
		String s1=typeString(t1);
		String s2=typeString(t2);
		if(!s2.equals(s1)) throw new MismatchException("type mismatch: "+s2+" <-> "+s1);
	}
	
	public static void typeEqual(ASTType t1,String s){
		String s1=typeString(t1);
		if(!s.equals(s1)) throw new MismatchException("type mismatch: "+s+" <-> "+s1);
	}
	
	public static void typeEqual(SimpleNode t1,String s){
		String s1=typeString(t1);
		if(!s.equals(s1)) throw new MismatchException("type mismatch: "+s+" <-> "+s1);
	}

	private static boolean isClassType(ASTType t){
		return t.token.image==null;
	}

}
