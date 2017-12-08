/*  Group Member
 *  Jinyang Li, Bingyu Song, Xin liu  */

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class q4 {

    Statement stmt = null;
    ResultSet result = null;
	Connection con = null;
    static ArrayList<q4staff> staffs;
	
	public q4(Connection con) throws SQLException {
		
		System.out.println("\n now working on question 4\n"
				+ "now will display the current staffs in all locations or still needs to assign\n");
		
		this.con = con;
		
		q4.staffs = new ArrayList<q4staff>();
		
		viewWorkers();
		q4Menu();
		
		
		
	}
	
	public int [] getLocations() throws SQLException {
		
		String sql = "select id from theatre";
	    stmt = con.createStatement();
	    result = stmt.executeQuery(sql);
	    
	    Vector <Integer> vi = new Vector<Integer>();
	    
	    while(result.next()) {
	    	
	      int id = result.getInt("id");
	      
	      vi.add(id);

	    }
	    int [] re = new int[vi.size()];
	    for (int i=0; i< vi.size(); i++)  {
	    	
	    	re[i] = vi.get(i);
	    	
	    }
	    
		return re;
		
		
	}
	
	String getAdress(int id) throws SQLException {
		
		String sql = "select location from theatre where ID=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, Integer.toString(id));
		ResultSet rs = pstmt.executeQuery(); 
	    	    
	    while(rs.next()) {
	    	
	      return rs.getString("location");
	      
	    }
		return null;
		
	}
	
	private void q4Menu() throws SQLException {
		
		boolean flag = true;
		Scanner in = new Scanner(System.in);
		do {
		System.out.println("\n What do u wanna do now? say: \n  "
				+ "S  ------- show Info about schedule    --------------------\n"
				+ "A  ------- assign or modify work schedule for staffs ------\n"
				+ "Q  ------- quit this menu,other key showthis menu again-----");
		
		switch (in.nextLine()) {
			
		case "S" :
			for (q4staff sf : staffs) {				
				sf.showInfo();				
			}
			break;
		case "A" :
			System.out.println("Which staff u wanna assign or modify? Enter the name CORRECTLY !");
			String name = in.nextLine();

			for (int i=0; i< staffs.size(); i++) {
				
				if (staffs.get(i).name.equalsIgnoreCase(name)) {
					System.out.println("Found! Enter the new start time, end time and locs id, leave n for no modyfing\n "
							+ "example: 2013-11-22,2013-12-33,2 \n"
							+ "the avaliable locs ids are : \n");
					
					int [] locsid = getLocations();
					for (int z =0 ; z<locsid.length; z++) {
						System.out.println("id: "+ locsid[z] + " " + "location: " + getAdress(locsid[z]) );
					}
					
					String cmd = in.nextLine();
					
					String [] cmds = cmd.split(",");
					
					if(staffs.get(i).checkSchedule(cmds[0],cmds[1],cmds[2])) {
					staffs.get(i).modifySchedule(cmds[0],cmds[1],cmds[2]);
					System.out.println(staffs.get(i).name+" SUCESSFULLY MODIFIED!");
					
					} else {
						System.out.println(staffs.get(i).name+" cannot working at that time or at that location due to there"
								+ "is already has a person working!");
					}
					
					
					
				}
				
			}
		break;
		case "Q":
			flag = false;
		break;
		
		}
		
		
		} while (flag == true);
		
		
	}
	
	
	
	public void viewWorkers() throws SQLException{
		
		String sql = "select name,id from staff";
	    stmt = con.createStatement();
	    result = stmt.executeQuery(sql);
	        	   
	    while(result.next()) {
	    	
	      String name = result.getString("NAME");
	      int id = result.getInt("id");
	      
	      staffs.add(new q4staff(name,id));
	      
	      
	    
	    }
	
	}
	
	public void setTime() {
		
	}
	
	class q4staff {
		
		String name;
		
		Vector<Integer> WorkTimeSchduleId;  
		
		Vector<String> locs, startTimes, endTimes;
		
		String type;
		
		int id, typeID;
		
		q4staff (String a, int id) throws SQLException {
			
			this.name = a;
			this.id = id;
			WorkTimeSchduleId = getWorkTimeID(); 
			
			startTimes = getStartTime();
			endTimes = getEndTime();
			locs = getLocation();
			type = getType();
			
			showInfo();
			
			
		}
		
		void modifySchedule(String startTime, String endTime, String loc) throws SQLException {
			
			String sql = "UPDATE schedule set StartTime =to_date('"+startTime+"','YYYY-MM-DD'), ENDTIME =to_date('"+endTime+"','YYYY-MM-DD'), THEATREID=?  where workerID =?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			

			pstmt.setInt(1, Integer.parseInt(loc));
			pstmt.setInt(2, id);
			
			pstmt.executeQuery();
			
			
		}
		
		boolean checkSchedule(String startTime, String endTime, String loc) throws SQLException {
			
			String sql = "select workerID from schedule where STARTTIME = to_date('"+startTime+"','YYYY-MM-DD') AND ENDTIME=to_date('"+startTime+"','YYYY-MM-DD') and TheatreID=?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);			
			pstmt.setInt(1, Integer.parseInt(loc));			
			ResultSet rs = pstmt.executeQuery();
			int wid;
			
			if(rs.next()) {
				return false;
			} 
			
			else {
			
			return true;
			}
		}
		



		Vector<Integer> getWorkTimeID() throws SQLException {
			
			String sql = "select workerid from schedule where workerID =?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(id));
			ResultSet rs = pstmt.executeQuery(); 
			
			Vector<Integer> wt = new Vector<Integer>();
			
			while (rs.next()) {
				wt.add(rs.getInt(1));
			}
			
			return wt;
			
		}
		
		void showInfo () {
			
			if(startTimes.isEmpty()) System.out.println("Name: "+ name + " Needs to assign");
			
			if(startTimes.size()==1&&locs.size()==1) {			
				System.out.println("\nName: "+ name +
					   " startTime" + startTimes.get(0)
					   + " "+ "endTime: " + endTimes.get(0) +" "+ "location: "+locs.get(0)
					   + " "+ "type: "+ type);
			}
			
			if (startTimes.size()>1)
			
			for (int i=0; i<startTimes.size(); i ++) {
			System.out.println("\nName: "+ name +
							   " startTime" + startTimes.get(i)
							   + " "+ "endTime: " + endTimes.get(i) +" "+ "location: "+locs.get(i)
							   + " "+ "type: "+ type);
			}
		}

		private Vector<String> getEndTime() {
			
			endTimes = new Vector<String>();
			
			try {
			String sql = "select ENDTIME from schedule where workerID =?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(id));
			ResultSet rs = pstmt.executeQuery(); 
			
			while(rs.next()) {
				endTimes.add(rs.getString("ENDTIME"));
			}
			
			rs.close();
			return endTimes;
			
			} catch (Exception e) {
				e.getMessage();
			}
			return endTimes;
			

		}

		private Vector<String> getLocation() {
			
			Vector<Integer> locsID = new Vector<Integer> ();
			locs = new Vector<String> ();
			
			try {
			String sql = "select theatreID from schedule where workerID =?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(id));
			ResultSet rs = pstmt.executeQuery(); 
			
			while(rs.next()) {
				locsID.add(rs.getInt("theatreID"));
			}
			
			rs.close();
			
			
			} catch (Exception e) {
				e.getMessage();
			}
			
			try {
				
				for (int i=0; i<locsID.size();i++) {
					
				String sql = "select location from theatre where id =?";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, Integer.toString(locsID.get(i)));
				ResultSet rs = pstmt.executeQuery(); 
				
				while(rs.next()) {
					locs.add(rs.getString("location"));
				}
				
				return locs;
				
				}
				
			} catch (Exception e) {
				e.getMessage();
			}
			return locs;
			
		}

		private Vector<String> getStartTime() {
			
			startTimes = new Vector<String>();
			
			try {
			String sql = "select STARTTIME from schedule where workerID =?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(id));
			ResultSet rs = pstmt.executeQuery(); 
			
			while(rs.next()) {
				startTimes.add(rs.getString("STARTTIME"));
			}
			
			rs.close();
			return startTimes;
			
			} catch (Exception e) {
				e.getMessage();
			}
			return endTimes;
			
		}
		
		private String getType() {
			
			try {
				
			String preSQL = "Select typeID from staff where ID=?";	
			PreparedStatement prepstmt = con.prepareStatement(preSQL);
			prepstmt.setInt(1, id);
			ResultSet prers = prepstmt.executeQuery();
			if(prers.next()) {
				this.typeID = prers.getInt("TYPEID");
			}
			
			String sql = "select typename from stafftype where ID =?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, typeID);
			ResultSet rs = pstmt.executeQuery(); 
			String out = " ";
			while(rs.next()) {
					out=rs.getString("typeName");
			}
			
			rs.close();
			return out;
			
			} catch (Exception e) {
				e.getMessage();
			}
			return null;
			
		}
		
		@Override
		public boolean equals (Object st) {
			
			q4staff sts = (q4staff)st;
			if (this.name.equalsIgnoreCase(((q4staff)sts).name)) return true;
			
			return false;
		
		}
		
		
		
		
	}
}
