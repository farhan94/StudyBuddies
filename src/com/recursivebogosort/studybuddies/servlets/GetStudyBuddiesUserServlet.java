package com.recursivebogosort.studybuddies.servlets;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.googlecode.objectify.Ref;
import com.recursivebogosort.studybuddies.entities.GroupMember;
import com.recursivebogosort.studybuddies.entities.StudyBuddiesUser;

public class GetStudyBuddiesUserServlet {
	
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
				JSONObject jo = new JSONObject();
				try {
					jo.put("uid", sbu.getId());
					jo.put("name", sbu.getName());
					jo.put("email", sbu.getEmail());
					jo.put("phone_number", sbu.getPhoneNumber());
					jo.put("email_notify", sbu.isSubscribedToEmails());
					jo.put("sms_notify",sbu.isSubscribedToSMS());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 JSONArray ar = new JSONArray();
				 ar.put(jo);
				 ArrayList<Ref<GroupMember>> groups = sbu.getAllGroups();
				 for(int i = 0; i < groups.size(); i++){
					 Ref<GroupMember> gmRef = groups.get(i);
					 GroupMember gm = gmRef.get();
					 jo = new JSONObject();
					 try {
						jo.put("group_uid", gm.getGroup().getId());
						if(gm.getGroup().getOwner().getUser().getId() == sbu.getId()){
							jo.put("is_owner", true);
						}
						else{
							jo.put("is_owner", false);
						}
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
