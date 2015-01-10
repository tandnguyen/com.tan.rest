package com.tan.dao;
import java.sql.*;
import org.codehaus.jettison.json.JSONArray;
import com.tan.util.ToJSON;

public class SchemaOracleEmployee extends OracleEmployee {
	
	public int insertIntoEMP_TABLE( 
			String FIRST_NAME, 
			String LAST_NAME,
			String DEPT,
			String TITLE)
			throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		
		try {
			/*
			 * If this was a real application, you should do data validation here
			 * before starting to insert data into the database.
			 * 
			 * Important: The primary key on PC_PARTS table will auto increment.
			 * 		That means the PC_PARTS_PK column does not need to be apart of the 
			 * 		SQL insert query below.
			 */
			conn = oracleEmployeesConnection();
			query = conn.prepareStatement("insert into EMP_TABLE1 " +
					"(FIRST_NAME, LAST_NAME, DEPT, TITLE) " +
					"VALUES ( ?, ?, ?, ? ) ");

			query.setString(1, FIRST_NAME);
			query.setString(2, LAST_NAME);
			query.setString(3, DEPT);
			query.setString(4, TITLE);

			System.out.println(query);
			query.executeUpdate(); //note the new command for insert statement

		} catch(Exception e) {
			e.printStackTrace();
			return 500; //if a error occurs, return a 500
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return 200;
	}
	

	public JSONArray queryReturnEmployeesDept(String department) throws Exception {
		

		
		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try {
			conn =  oracleEmployeesConnection();
			query = conn.prepareStatement("select FIRST_NAME, LAST_NAME, DEPT, TITLE from EMP_TABLE1 where UPPER(DEPT) = ? ");
			
			query.setString(1, department.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();
			
			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return json;
	}
}
