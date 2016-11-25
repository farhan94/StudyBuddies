package com.recursivebogosort.studybuddies.servlets;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.googlecode.objectify.Ref;
import com.recursivebogosort.studybuddies.entities.Event;
import com.recursivebogosort.studybuddies.entities.GroupMember;
import com.recursivebogosort.studybuddies.entities.StudyBuddiesUser;

public class GetAllUserEventsServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		// 
		if (user != null) {
			String id = user.getUserId();
			System.out.println(ofy().load().keys().list());
			Ref<StudyBuddiesUser> sbuRef = ofy().load().type(StudyBuddiesUser.class).id(id);
			StudyBuddiesUser sbu = sbuRef.get();
			if(sbu != null){

				 JSONArray ar = new JSONArray();
				 ArrayList<Ref<Event>> events = sbu.getEvents();
				 for(int i = 0; i < events.size(); i++){
					 Ref<Event> eventRef = events.get(i);
					 Event ev = eventRef.get();
					 JSONObject jo = new JSONObject();
					 try {
						jo.put("event_uid", ev.getId());
						jo.put("event_date", ev.getDate());
						jo.put("event_name", ev.getEventName());
						jo.put("event_location", ev.getEventLocation());
						jo.put("event_description", ev.getEventDescription());

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 ar.put(jo);
				 }
				 

			        resp.setContentType("application/json");

			        resp.getWriter().print(ar);
				 
			}

		}
		

	}
	
}
