package com.demo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.demo.model.Admin;

public class StudentService {
	
	Connection con=MyConnection.getConnection();
	
	public Admin getCredential() throws SQLException {
		
		PreparedStatement pt=con.prepareStatement("select * from admin");
		
		ResultSet rs=pt.executeQuery();
		
		Admin a=null;
		while(rs.next()) {
			a=new Admin(rs.getInt(1),rs.getString(2),rs.getString(3));
		}
		return a;
	}
}
