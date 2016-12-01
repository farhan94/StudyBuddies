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
//		UserService userService = UserServiceFactory.getUserService();
//		User user = userService.getCurrentUser();
		String userID = req.getParameter("userID");
		StudyBuddiesUser sbu = ofy().load().type(StudyBuddiesUser.class).id(userID).getValue();
		// 

			if(sbu != null){

				 JSONArray ar = new JSONArray();
				 ArrayList<Ref<Event>> events = sbu.getEvents();
				 for(int i = 0; i < events.size(); i++){
					 Ref<Event> eventRef = events.get(i);
					 eventRef = ofy().load().ref(eventRef);
					 Event ev = eventRef.get();
					 JSONObject jo = new JSONObject();
					 try {
						jo.put("uid", ev.getId());
						jo.put("date", ev.getDate());
						jo.put("name", ev.getEventName());
						jo.put("location", ev.getEventLocation());
						jo.put("description", ev.getEventDescription());

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
