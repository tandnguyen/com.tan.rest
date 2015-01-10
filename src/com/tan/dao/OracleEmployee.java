package com.tan.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.*;
import javax.sql.*;
public class OracleEmployee {
	private static DataSource OracleEmployee = null;
	private static Context initContext = null;
	
	public static DataSource OracleEmployeesConn() throws Exception{
		
		try {
			if (initContext == null){
				initContext = new InitialContext();						
			}
			
			OracleEmployee= (DataSource) initContext.lookup("java:/comp/env/jdbc/EMPLOYEES");

		}
		catch (Exception e){
			System.out.println("Not able to find Datasource");
			e.printStackTrace();
		}
		
		return OracleEmployee;
	}
	
	protected static Connection oracleEmployeesConnection(){
		Connection conn = null;
		try {
			conn = OracleEmployeesConn().getConnection();
			return conn;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return conn;
	}
}
