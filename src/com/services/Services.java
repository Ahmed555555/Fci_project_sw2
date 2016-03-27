package com.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.json.simple.JSONObject;

import com.models.DBConnection;
import com.models.UserModel;

@Path("/")
public class Services {

	/*
	 * @GET
	 * 
	 * @Path("/signup")
	 * 
	 * @Produces(MediaType.TEXT_HTML) public Response signUp(){ return
	 * Response.ok(new Viewable("/Signup.jsp")).build(); }
	 */

	@POST
	@Path("/signup")
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@FormParam("name") String name,
			@FormParam("email") String email, @FormParam("pass") String pass) {
		UserModel user = UserModel.addNewUser(name, email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}

	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("email") String email,
			@FormParam("pass") String pass) {
		UserModel user = UserModel.login(email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}
	
	@POST
	@Path("/updatePosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePosition(@FormParam("id") String id,
			@FormParam("lat") String lat, @FormParam("long") String lon) {
		Boolean status = UserModel.updateUserPosition(Integer.parseInt(id), Double.parseDouble(lat), Double.parseDouble(lon));
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String getJson() {
		return "Hello after editing";
		// Connection URL:
		// mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/
	}
	
	@POST
	@Path("/follow")
	@Produces(MediaType.TEXT_PLAIN)
	public Boolean follow(@FormParam("Follower_email") String follower_email , @FormParam("pass") String pass ,
			@FormParam("Followed_email") String followed_email){
				return UserModel.follow(follower_email, pass, followed_email);
				
	}
		
	@POST
	@Path("/unfollow")
	@Produces(MediaType.TEXT_PLAIN)
	public Boolean unfollow(@FormParam("Follower_email") String follower_email , @FormParam("pass") String pass ,
			@FormParam("Followed_email") String followed_email){
				return UserModel.unfollow(follower_email, pass, followed_email);
	}	
		
		
	@GET
	@Path("/getUserLastPosition/{email}/{pass}")
	@Produces(MediaType.TEXT_PLAIN)
	public String user_position(@PathParam("email") String email , @PathParam("pass") String pass){
		JSONObject object = new JSONObject();
		UserModel user = UserModel.login(email, pass);
		object.put("lon", user.getLon());
		object.put("lat", user.getLat());
		return object.toJSONString();
	}
	@GET
	@Path("/getFollowers/{email}/{pass}")
	@Produces(MediaType.TEXT_PLAIN)
	public String get_follower(@PathParam("email") String email , @PathParam("pass") String pass) throws SQLException {
		ArrayList<String> s = new ArrayList<>();
		JSONObject object = new JSONObject();
		if(UserModel.login(email, pass) != null){
			s = UserModel.get_followers(email , pass);
			for(int x = 0 ; x < s.size() ; x++){
				object.put("follower"+x, s.get(x));
			}
			
			
		}
		return object.toJSONString();
	}
	
		
}
	
