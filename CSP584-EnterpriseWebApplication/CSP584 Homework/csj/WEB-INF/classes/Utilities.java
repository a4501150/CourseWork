import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Utilities extends HttpServlet{
		
	PrintWriter pw;
	HttpServletRequest request;

	public Utilities(HttpServletRequest request, PrintWriter pw) {

		this.pw = pw;
		this.request = request;
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	public void printHtml(String file) {
		
		String result = HtmlToString(file);

		HttpSession session=request.getSession(false);
		
		if (file == "Header_Nav.html") {
			
			if (session.getAttribute("username")!=null && (Boolean)session.getAttribute("isLogin")){
				
				String username = session.getAttribute("username").toString();
				username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
				result = result
				+ "<li><a>Hello, "+username+"</a></li>"
				+ "<li><a href='Account'>Account</a></li>"
				+ "<li><a href='Logout'>Logout</a></li>"
				+ "\r\n" + 
				"        </ul>\r\n" + 
				"    </nav>";
				
			}
			else
				result = result + "<li><a href='Login'>Login</a></li>" + 
						"<li><a href='Signup'>Signup</a></li>"
						+ "\r\n" + 
						"        </ul>\r\n" + 
						"    </nav>"
						;
			
			//result = result + "<li><a href='Cart'>Cart("+CartCount()+")</a></li></ul></div></div><div id='page'>";
			pw.print(result);
			
		} else
			
			pw.print(result);
		
		}
	
	public String HtmlToString(String file) {
		
		
		StringBuilder contentBuilder = new StringBuilder();
		try {
		    BufferedReader in = new BufferedReader(new InputStreamReader(request.getServletContext().getResourceAsStream("static\\"+file)));
		    String str;
		    while ((str = in.readLine()) != null) {
		        contentBuilder.append(str);
		    }
		    in.close();
		} catch (IOException e) {
			e.printStackTrace(pw);
		}
		String content = contentBuilder.toString();
		return content;
		
	}
	
	
	//without decimal digits 
	public static String toPercentage(double n){
	    return String.format("%.0f",n*100)+"%";
	}

	public int CartCount() {
		
		
		
		return 0;
		
	}
	
	
}
