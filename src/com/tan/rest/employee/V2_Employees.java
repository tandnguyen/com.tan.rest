package com.tan.rest.employee;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import com.tan.dao.SchemaOracleEmployee;

@Path("/v2/employees")
public class V2_Employees {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnDeptPersonel (
			@QueryParam("department") String department) throws Exception {
		String returnString = null;
		JSONArray json = new JSONArray();
		try {
			if (department == null){
				return Response.status(400).entity("Error: Please specify department for this search").build();
			}
			SchemaOracleEmployee dao = new SchemaOracleEmployee();
			json = dao.queryReturnEmployeesDept(department);
			returnString = json.toString();
			
		}
		catch (Exception e){
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		return Response.ok(returnString).build();
	}

}
