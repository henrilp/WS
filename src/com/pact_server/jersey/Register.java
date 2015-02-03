package com.pact_server.jersey;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
//Path: http://localhost/<appln-folder-name>/register
@Path("/register")
public class Register {
	// HTTP Get Method
	@GET 
	// Path: http://localhost/<appln-folder-name>/register/doregister
	@Path("/doregister")  
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?pseudo=pqrs&password=abc&firstname=xyz&last_name=cdf&email=hij
	public String doLogin(@QueryParam("pseudo") String pseudo, @QueryParam("password") String password, @QueryParam("first_name") String first_name, @QueryParam("last_name") String last_name, @QueryParam("email") String email){
		String response = "";
		//System.out.println("Inside doLogin "+uname+"  "+pwd);
		int retCode = registerUser(pseudo, password, first_name, last_name, email);
		if(retCode == 0){
			response = Utility.constructJSON("register",true);
		}else if(retCode == 1){
			response = Utility.constructJSON("register",false, "You are already registered");
		}else if(retCode == 2){
			response = Utility.constructJSON("register",false, "Special Characters are not allowed in Pseudo and Password");
		}else if(retCode == 3){
			response = Utility.constructJSON("register",false, "Error occured");
		}
		return response;
				
	}
	
	private int registerUser(String pseudo, String password, String first_name, String last_name, String email){
		System.out.println("Inside checkCredentials");
		int result = 3;
		if(Utility.isNotNull(pseudo) && Utility.isNotNull(password)){
			try {
				if(DBConnection.insertUser(pseudo, password, first_name, last_name, email)){
					System.out.println("RegisterUSer if");
					result = 0;
				}
			} catch(SQLException sqle){
				System.out.println("RegisterUSer catch sqle");
				//When Primary key violation occurs that means user is already registered
				if(sqle.getErrorCode() == 1062){
					result = 1;
				} 
				//When special characters are used in pseudo, password, first_name, last_name, email)
				else if(sqle.getErrorCode() == 1064){
					System.out.println(sqle.getErrorCode());
					result = 2;
				}
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Inside checkCredentials catch e ");
				result = 3;
			}
		}else{
			System.out.println("Inside checkCredentials else");
			result = 3;
		}
			
		return result;
	}
	
}

