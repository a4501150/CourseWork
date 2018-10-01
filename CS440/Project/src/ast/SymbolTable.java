package ast;
import java.util.Map;
import java.util.TreeMap;

import exception.DuplicateException;
import exception.NotDefineException;


public class SymbolTable {
	
	SymbolTable(){
		classes=new TreeMap<String, ClassSymbolTable>();
	}
	
	public Map<String,ClassSymbolTable> classes;
	
	public static class ClassSymbolTable{
		ClassSymbolTable(){
			fields=new TreeMap<String, ASTType>();
			methods=new TreeMap<String, MethodSymbolTable>();
			parent=null;
		}
		public Map<String,ASTType> fields;
		public Map<String,MethodSymbolTable> methods;
		public String parent;
	}
	
	public static class MethodSymbolTable{
		MethodSymbolTable(ASTType t){
			params=new TreeMap<String, ASTType>();
			locals=new TreeMap<String, ASTType>();
			returnType=t;
		}
		public ASTType returnType;
		public Map<String,ASTType> params;
		public Map<String,ASTType> locals;
	}

	public ClassSymbolTable addClass(String image) {
		if(classes.get(image)!=null) throw new DuplicateException("class "+image+" already exsits.");
		ClassSymbolTable cst=new ClassSymbolTable();
		classes.put(image, cst);
		return cst;
	}
	
	public void addField(String image,ASTType type,String clazz){
		ClassSymbolTable t=classes.get(clazz);
		if(t.fields.get(image)!=null)  throw new DuplicateException("field "+image+" already exsits in class "+clazz);
		t.fields.put(image,type);
	}

	public void addLocal(String image, ASTType type, String methodName,
			String clazzName) {
		ClassSymbolTable t=classes.get(clazzName);
		MethodSymbolTable m=t.methods.get(methodName);
		if(m.locals.get(image)!=null) throw new DuplicateException("local "+image+" already exsits in class "+clazzName +" method "+methodName);
		m.locals.put(image, type);
	}
	
	public void addParam(String image, ASTType type, String methodName,
			String clazzName) {
		ClassSymbolTable t=classes.get(clazzName);
		MethodSymbolTable m=t.methods.get(methodName);
		if(m.params.get(image)!=null) throw new DuplicateException("param "+image+" already exsits in class "+clazzName +" method "+methodName);
		m.params.put(image, type);
	}

	public void addMethod(String methodName,ASTType type, String clazz) {
		ClassSymbolTable t=classes.get(clazz);
		if(t.methods.get(methodName)!=null)  throw new DuplicateException("method "+methodName+" already exsits in class "+clazz);
		MethodSymbolTable m =new MethodSymbolTable(type);
		t.methods.put(methodName, m);
	}
	
	public ASTType findLocalOrParam(String image,String methodName,String clazzName){
		ClassSymbolTable t=classes.get(clazzName);
		MethodSymbolTable m=t.methods.get(methodName);
		ASTType ret = m.locals.get(image);
		if(ret!=null) return ret;
		ret=m.params.get(image);
		if(ret!=null) return ret;
		ret=t.fields.get(image);
		if(ret!=null) return ret;
		throw new NotDefineException("variable "+image+" not exsits in class "+clazzName +" method "+methodName);
	}
	
	public ClassSymbolTable findClass(String image) {
		ClassSymbolTable cst=classes.get(image);
		if(cst==null) throw new NotDefineException("class "+image+" not exsits.");
		return cst;
	}

	public MethodSymbolTable findMethod(String clazz, String method) {
		ClassSymbolTable cst=findClass(clazz);
		MethodSymbolTable m=cst.methods.get(method);
		if(m==null)  throw new NotDefineException("method "+method+" not exsits.");
		return m;
	}
	
}


