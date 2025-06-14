package com.abbYs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abbYs.model.RoleModel;
import com.abbYs.repository.RoleRepository;
@Repository
public class UserDAO {
	@Autowired
    private DataSource dataSource;
	@Autowired
	RoleRepository repository;
	
	public String validateUser(String email, String password) {
		String sql = "SELECT * FROM tbl_user WHERE email = ? AND password = ?";
		try(Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1, email);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				int roleId = rs.getInt("role_id");
				RoleModel role = repository.findById(roleId).get();
				return role.getRoleName(); // Return the role name if user is valid
			}
			else {
				return "Invalid credentials"; // Return false if user not found
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			return e.getMessage(); // Return false if an error occurs
		}
	}
}
