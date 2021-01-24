package com.capgemini.utility;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCConnection {
	
	public static Connection connectdb() {
	try {
	Class.forName("com.mysql.jdbc.Driver");
	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/account","root","root");
	return conn;
	}
	catch(Exception e) {
		e.printStackTrace();
		return null;
	}
}
}