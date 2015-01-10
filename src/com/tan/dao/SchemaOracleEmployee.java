package com.tan.dao;
import java.sql.*;
import org.codehaus.jettison.json.JSONArray;
import com.tan.util.ToJSON;

public class SchemaOracleEmployee extends OracleEmployee {

	public JSONArray queryReturnEmployeesDept(String department) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try {
			conn =  oracleEmployeesConnection();
			query = conn.prepareStatement("select EMP_NUMBER, FIRST_NAME, LAST_NAME, DEPT, TITLE, HIRE_DATE from EMP_TABLE where UPPER(DEPT) = ? ");
			
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
