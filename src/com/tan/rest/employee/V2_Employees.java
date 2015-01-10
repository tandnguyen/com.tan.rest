package com.tan.rest.employee;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
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
	
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcParts(String incomingData) throws Exception {
		
		String returnString = null;
		JSONArray jsonArray = new JSONArray(); 
		SchemaOracleEmployee dao = new SchemaOracleEmployee();
		
		try {
			System.out.println("incomingData: " + incomingData);
			
			/*
			 * ObjectMapper is from Jackson Processor framework
			 * http://jackson.codehaus.org/
			 * 
			 * Using the readValue method, you can parse the json from the http request
			 * and data bind it to a Java Class.
			 */
			ObjectMapper mapper = new ObjectMapper();  
			ItemEntry itemEntry = mapper.readValue(incomingData, ItemEntry.class);
			
			int http_code = dao.insertIntoEMP_TABLE(itemEntry.FIRST_NAME, 
													itemEntry.LAST_NAME, 
													itemEntry.DEPT, 
													itemEntry.TITLE );
			
			if( http_code == 200 ) {
				//returnString = jsonArray.toString();
				returnString = "Item inserted";
			} else {
				return Response.status(500).entity("Unable to process Item").build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
}


class ItemEntry {
	public String FIRST_NAME ;
	public String LAST_NAME ;
	public String DEPT;
	public String TITLE;
}