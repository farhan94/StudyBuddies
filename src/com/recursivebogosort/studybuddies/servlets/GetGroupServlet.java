package com.recursivebogosort.studybuddies.servlets;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Id;
import com.recursivebogosort.studybuddies.entities.Course;
import com.recursivebogosort.studybuddies.entities.Event;
import com.recursivebogosort.studybuddies.entities.Group;
import com.recursivebogosort.studybuddies.entities.StudyBuddiesUser;
import com.recursivebogosort.studybuddies.entities.University;

public class GetGroupServlet extends HttpServlet{

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//long id = ofy().load().type(Course.class).first().getValue().getId();
		String cIDString = req.getParameter("courseID");
		long cID = Long.parseLong(cIDString);
		Ref<Course> courseRef = ofy().load().type(Course.class).id(cID);
		Course course = ofy().load().ref(courseRef).getValue();
		Collection<Group> groups =  ofy().load().refs(course.getGroupRefs()).values();
		Object[] g = groups.toArray();
		JSONArray ar = new JSONArray();
		for(int i = 0; i < g.length; i++){
			Group grp = (Group) g[i];
			JSONObject jo = new JSONObject();
			try {
				jo.put("uid", grp.getId().toString());
				jo.put("name", grp.getGroupName());
				jo.put("size", grp.getCurrentSize());
				jo.put("purpose", grp.getGroupDescription());
				jo.put("isMember", true);
				jo.put("icon_url","google.com");
			 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ar.put(jo);
		}
//		Ref<StudyBuddiesUser> sbuRef = ofy().load().type(StudyBuddiesUser.class).id(id);
		//JSONObject jo = new JSONObject(groups.toArray());
		
//		try {
//			jo.put("Name", sbu.getName());
//			jo.put("Email", sbu.getEmail());
//			jo.put("PhoneNumber", sbu.getPhoneNumber());
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		resp.setContentType("application/json");
		
		resp.getWriter().print(ar);
		//}
		//super.doGet(req, resp);
		
		
	}
	

	
}
