/*  Group Member
 *  Jinyang Li, Bingyu Song, Xin liu  */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;


public class q5 {
	
    Statement stmt = null;
    ResultSet result = null;
	Connection con = null;
    static Vector<String> status;

	public q5(Connection con) throws SQLException {
		
		
		
		this.con = con;

		while(true) {
		
		System.out.println("Which status reward rates u wanna modify enter q for exiting? \n");
		
		showRates();
		
		Scanner in = new Scanner(System.in);
		
		String name = in.nextLine();
		
		if (name.equalsIgnoreCase("q")) break;
		
		System.out.println("enter the new rate with a valid integer!");
		
		int rate = Integer.parseInt(in.nextLine());

		if (checkLimit(name, rate)) {
			modify(name, rate);
		} else {
			System.out.println("over Limit!!");
		}
		
		
		
		}
		
	}
	
	void showRates() throws SQLException {
		
		String sql = "select * from STATUS_POINTS_RATE";
		
		PreparedStatement pstmt = con.prepareStatement(sql);			
		ResultSet rs = pstmt.executeQuery(); 
		while(rs.next())
			System.out.print("MemberStatus Name: "+rs.getString("StatusName")+
							" currRate: "+ rs.getInt("RATE") + " \n" );
		
	}
	
	boolean checkLimit(String in, int newRate) throws SQLException{
		
		if (in.equalsIgnoreCase("SIVLER")) {
			
			String sql = "select rate from STATUS_POINTS_RATE where STATUSNAME =?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "GOLD");			
			ResultSet rs = pstmt.executeQuery(); 
			rs.next();
			int gold_rate = rs.getInt("RATE");
			
			if (newRate>gold_rate) return false;
				return true;
			
		}
		
		if (in.equalsIgnoreCase("GOLD")) {
			
			String sql = "select rate from STATUS_POINTS_RATE where STATUSNAME =?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "PLAT");			
			ResultSet rs = pstmt.executeQuery(); 
			rs.next();
			int gold_rate = rs.getInt("RATE");
			
			if (newRate>gold_rate) return false;
				return true;
			
		}
		
		if (in.equalsIgnoreCase("DIAMOND")) {
			
			String sql = "select rate from STATUS_POINTS_RATE where STATUSNAME =?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "CHALLENGER");			
			ResultSet rs = pstmt.executeQuery(); 
			rs.next();
			int gold_rate = rs.getInt("CHALLENGER");
			
			if (newRate>gold_rate) return false;
				return true;
			
		}
		
		if (in.equalsIgnoreCase("CHALLENGER")) {
			
				return true;
			
		}
		
		System.out.println("Enter a correct stauts!");
		return false;

		
	}
	
	void modify(String in, int rate) throws SQLException{
		
		String sql = "UPDATE STATUS_POINTS_RATE set RATE =? where STATUSNAME=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(2, in);
		pstmt.setInt(1, rate);
		pstmt.executeQuery(); 
		
		
	}

	
	
	
	
	
}
