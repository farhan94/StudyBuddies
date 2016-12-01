package com.recursivebogosort.studybuddies.servlets;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.googlecode.objectify.Ref;
import com.recursivebogosort.studybuddies.entities.Course;
import com.recursivebogosort.studybuddies.entities.Event;
import com.recursivebogosort.studybuddies.entities.Group;

public class GetEventsServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//long id = ofy().load().type(Course.class).first().getValue().getId();
		String cIDString = req.getParameter("groupID");
		long cID = Long.parseLong(cIDString);
		Ref<Group> groupRef = ofy().load().type(Group.class).id(cID);
		Group group = ofy().load().ref(groupRef).getValue();
		Collection<Event> events =  ofy().load().refs(group.getEventRefs()).values();
		Object[] g = events.toArray();
		JSONArray ar = new JSONArray();
		for(int i = 0; i < g.length; i++){
			Event evnt = (Event) g[i];
			JSONObject jo = new JSONObject();
			try {
				jo.put("uid", evnt.getId().toString());
				jo.put("name", evnt.getEventName());
				jo.put("description", evnt.getEventDescription());
				jo.put("date", evnt.getDate());
				//jo.put("time", "I dunno");
				jo.put("location",evnt.getEventLocation());
				
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
