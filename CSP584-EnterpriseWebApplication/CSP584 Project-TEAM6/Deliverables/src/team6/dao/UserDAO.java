package team6.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import team6.entity.Role;
import team6.entity.User;

public class UserDAO {

	private Connection conn;
	
	public UserDAO() {
		conn = MySQLDatabaseOperator.INSTANCE.getConnection();
	}
	
	public User selectUser(int uid) {
		String sql = "SELECT * from csp584_project.user WHERE uid = ?";
		User user = null;
		
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();
			user = buildUser(rs);
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return user; 
	}

	public User selectUser(String username) {
		String sql = "SELECT * from csp584_project.user WHERE username = ?";
		User user = null;
		
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			user = buildUser(rs);
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return user; 
	}

	/**
	 * Select user in MySQL given username and password 
	 */
	public User selectUser(String username, String password) {
		String sql = "SELECT * from csp584_project.user WHERE username = ? AND password = ?";
		User user = null;
		
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			user = buildUser(rs);
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return user; 
	}

	/**
	 * Insert user into MySQL given parameter 
	 * @param role 
	 */
	public void insertUser(String username, String password, String email, Role role) {
		String sql = "INSERT INTO `csp584_project`.`user` (`username`, `password`, `email`, `role`)"
					+ " VALUES (?, ?, ?, ?)";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.setString(4, role.toString().toLowerCase());
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Build User object from result set 
	 */
	private User buildUser(ResultSet rs) {
		User user = null;
		
		try {
			if(rs.first()) {	// found result
				user = new User();
				user.setuId(rs.getInt("uid"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				switch(rs.getString("role")) {
					case "manager":
					{
						user.setRole(Role.MANAGER);
						break;
					}
					case "staff":
					{
						user.setRole(Role.STAFF);
						break;
					}
					case "customer":
					{
						user.setRole(Role.CUSTOMER);
						break;
					}
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}

}
