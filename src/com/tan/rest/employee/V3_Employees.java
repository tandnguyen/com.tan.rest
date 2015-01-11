package com.tan.rest.employee;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.tan.dao.SchemaOracleEmployee;

@Path("/v3/employees")
public class V3_Employees {

	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response addEmployee(String incomingData) throws Exception {
		
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		SchemaOracleEmployee dao = new SchemaOracleEmployee();
		
		try {
			
			/*
			 * We can create a new instance and it will accept a JSON string
			 * By doing this, we can now access the data.
			 */
			JSONObject employeeData = new JSONObject(incomingData);
			System.out.println( "jsonData: " + employeeData.toString() );
			

			int http_code = dao.insertIntoEMP_TABLE(employeeData.optString("FIRST_NAME"), 
														employeeData.optString("LAST_NAME"), 
														employeeData.optString("DEPT"), 
														employeeData.optString("TITLE"), employeeData.optString("BAND") );
			
			if( http_code == 200 ) {
				/*
				 * The put method allows you to add data to a JSONObject.
				 * The first parameter is the KEY (no spaces)
				 * The second parameter is the Value
				 */
				jsonObject.put("HTTP_CODE", "200");
				jsonObject.put("MSG", "Item has been entered successfully, Version 3");
				/*
				 * When you are dealing with JSONArrays, the put method is used to add
				 * JSONObjects into JSONArray.
				 */
				returnString = jsonArray.put(jsonObject).toString();
			} else {
				return Response.status(500).entity("Unable to enter Item").build();
			}
			
			System.out.println( "returnString: " + returnString );
			
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
	
	@Path("/{first}/{last}")
	@PUT
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateItem(@PathParam("first") String first,
									@PathParam("last") String last,
									String incomingData) 
								throws Exception {
		
		System.out.println("incomingData: " + incomingData);
		System.out.println("first_name: " + first);
		System.out.println("last_name: " + last);
		
		int id;
		int band;
		int http_code;
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		SchemaOracleEmployee dao = new SchemaOracleEmployee();
		
		try {
			
			JSONObject employeeData = new JSONObject(incomingData); //we are using json objects to parse data
			id = employeeData.optInt("ID", 0);
			band = employeeData.optInt("BAND", 0);
			System.out.println ("ID: " + id + " BAND: " + band);
			//call the correct sql method
			http_code = dao.updateEMP_TABLE(id, band);
			
			if(http_code == 200) {
				jsonObject.put("HTTP_CODE", "200");
				jsonObject.put("MSG", "Item has been updated successfully");
			} else {
				return Response.status(500).entity("Server was not able to process your request").build();
			}
			
			returnString = jsonArray.put(jsonObject).toString();
			
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
	

}
