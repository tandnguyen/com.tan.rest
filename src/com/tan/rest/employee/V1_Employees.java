package com.tan.rest.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Path;




import org.codehaus.jettison.json.JSONArray;

import com.tan.dao.OracleEmployee;
import com.tan.util.ToJSON;

@Path("/v1/employees")
public class V1_Employees {
	private static final String api_version= "00.01.14";
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllEmployees() throws Exception{
		PreparedStatement query = null;
		String returnString = null;
		Connection conn = null;
		Response rb = null;
		try {
			conn = OracleEmployee.OracleEmployeesConn().getConnection();
			query = conn.prepareStatement("select * from EMP_TABLE");
			ResultSet rs = query.executeQuery();
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			json = converter.toJSONArray(rs);
			query.close();

			returnString = json.toString();
			rb = Response.ok(returnString).build();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			if (conn != null) conn.close();
		}
		return rb;
	}
	
	
	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion(){
		return "<p>Version: </p>" + api_version;
		
	}
	

	@Path ("/database")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnDatabaseStatus() throws Exception {
		
		PreparedStatement query = null;
		String sysDate = null;
		String returnString = null;
		Connection conn = null;
		
		try {
			conn = OracleEmployee.OracleEmployeesConn().getConnection();
			query = conn.prepareStatement("select to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS') DATETIME " + "from sys.dual");
			ResultSet rs = query.executeQuery();
			
			while (rs.next()) {
				sysDate = rs.getString("DATETIME");
			}
			query.close();
			
			returnString = "<p>Database Status</p> " + "<p>Database Date/Time return: " + sysDate + "</p>";
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return returnString;
	}

	
}
