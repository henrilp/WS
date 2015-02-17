package com.pact_server.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

//Path: http://localhost/<appln-folder-name>/tag
@Path("/tag")
public class Tag {
	// HTTP Get Method
		@GET 
		// Path: http://localhost/<appln-folder-name>/tag/addtag
		@Path("/addtag")
		// Produces JSON as response
		@Produces(MediaType.APPLICATION_JSON) 
		// Query parameters are parameters: http://localhost/<appln-folder-name>/tag/addtag?pseudo=abc&object_name=xyz&picture=url
		public String addTag(@QueryParam("pseudo") String pseudo, @QueryParam("object_name") String object_name, @QueryParam("picture") String picture){
			String response = "";
			if(checkCredentials(pseudo, object_name, picture)){
				response = Utility.constructJSON("addtag",true);
			}else{
				response = Utility.constructJSON("addtag", false, "A problem has occured");
			}
		return response;		
		}
		
		private boolean checkCredentials(String pseudo, String object_name, String picture){
			System.out.println("Inside checkCredentials");
			boolean result = false;
			if(Utility.isNotNull(pseudo) && Utility.isNotNull(object_name)&&Utility.isNotNull(picture)){
				try {
					result = DBConnection.insertTag(pseudo, object_name, picture);
					//System.out.println("Inside checkCredentials try "+result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//System.out.println("Inside checkCredentials catch");
					result = false;
				}
			}else{
				//System.out.println("Inside checkCredentials else");
				result = false;
			}
				
			return result;
		}
		
		// HTTP Get Method
				@GET 
				// Path: http://localhost/<appln-folder-name>/tag/deletetag
				@Path("/deletetag")
				// Produces JSON as response
				@Produces(MediaType.APPLICATION_JSON) 
				// Query parameters are parameters: http://localhost/<appln-folder-name>/tag/deletetag?pseudo=abc&object_name=xyz
				public String addTag(@QueryParam("pseudo") String pseudo, @QueryParam("object_name") String object_name){
					String response = "";
					if(checkDeleteTag(pseudo, object_name)){
						response = Utility.constructJSON("deletetag",true);
					}else{
						response = Utility.constructJSON("deletetag", false, "A problem has occured");
					}
				return response;		
				}
				
				private boolean checkDeleteTag(String pseudo, String object_name){
					System.out.println("Inside checkDeleteTag");
					boolean result = false;
					if(Utility.isNotNull(pseudo) && Utility.isNotNull(object_name)){
						try {
							result = DBConnection.deleteTag(pseudo, object_name);
							//System.out.println("Inside checkDeleteTag try "+result);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							//System.out.println("Inside checkDeleteTag catch");
							result = false;
						}
					}else{
						//System.out.println("Inside checkDeleteTag else");
						result = false;
					}
						
					return result;
				}
				
				// HTTP Get Method
				@GET 
				// Path: http://localhost/<appln-folder-name>/tag/deletetag
				@Path("/retrievetag")
				// Produces JSON as response
				@Produces(MediaType.APPLICATION_JSON) 
				// Query parameters are parameters: http://localhost/<appln-folder-name>/tag/deletetag?pseudo=abc&object_name=xyz
				public String retrieveTag(@QueryParam("pseudo") String pseudo){
					String response = "";
					if(checkRetrieveTag(pseudo)){
						JSONObject response1 = new JSONObject();
						try {
							response1.put("retrievetag",true);
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
						}
					}else{
						response = Utility.constructJSON("deletetag", false, "A problem has occured");
					}
				return response.toString();		
				}
				
				private boolean checkRetrieveTag(String pseudo){
					System.out.println("Inside checkDeleteTag");
					boolean result = false;
					if(Utility.isNotNull(pseudo)){
						try {
							result = DBConnection.retrieveTag(pseudo);
							//System.out.println("Inside checkDeleteTag try "+result);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							//System.out.println("Inside checkDeleteTag catch");
							result = false;
						}
					}else{
						//System.out.println("Inside checkDeleteTag else");
						result = false;
					}
						
					return result;
				}
}
