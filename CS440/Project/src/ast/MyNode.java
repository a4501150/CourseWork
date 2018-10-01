package ast;
import parser.*;
public class MyNode{
	public Token token;

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
	
	public String tokenImage(){
		  if(token!=null){
			  if(token.image!=null) return token.image;
			  else return token.toString();
		  }
		  return "";
	}

}
