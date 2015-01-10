package com.tan.dao;
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
}
