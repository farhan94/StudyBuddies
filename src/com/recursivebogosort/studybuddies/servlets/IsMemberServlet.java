package com.recursivebogosort.studybuddies.servlets;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
import com.googlecode.objectify.Ref;
import com.recursivebogosort.studybuddies.entities.Group;
import com.recursivebogosort.studybuddies.entities.GroupMember;
import com.recursivebogosort.studybuddies.entities.GroupOwner;
import com.recursivebogosort.studybuddies.entities.StudyBuddiesUser;

public class IsMemberServlet extends HttpServlet{

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//long id = ofy().load().type(Course.class).first().getValue().getId();
		String cIDString = req.getParameter("groupID");
		long cID = Long.parseLong(cIDString);
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        String userID = req.getParameter("userID");
        StudyBuddiesUser sbu = ofy().load().type(StudyBuddiesUser.class).id(userID).getValue();
        ArrayList<Ref<GroupMember>> gmrefs = sbu.getOtherGroups();
        boolean tf = false;
        ArrayList<Ref<GroupOwner>> gorefs = sbu.getMyGroups();
        JSONArray ja = new JSONArray();
        if(gorefs != null){
        Iterator<Ref<GroupOwner>> it = gorefs.iterator();
	        while(it.hasNext()){
	        	Ref<GroupOwner> goref = it.next();
	        	if(goref != null){
	        		goref = ofy().load().ref(goref);
	        		GroupOwner go = goref.get();
	        		if(go != null){
	        			if(go.getGroup().getId() == cID){
	        				tf = true;
	        				break;
	        			}
	        		}
	        	}
	        }
        }
        if(tf == false){
        	if(gmrefs != null){
                //Collection<GroupMember> memberGroups = ofy().load().refs(sbu.getAllGroups()).values();
                Iterator<Ref<GroupMember>> iter = gmrefs.iterator();
                
                while(iter.hasNext()){
                	Ref<GroupMember> gmref = iter.next();
                	if(gmref != null){
                		gmref = ofy().load().ref(gmref);
                		GroupMember gm = gmref.get();
                		if(gm.getGroup().getId() == cID){
	        				tf = true;
	        				break;
	        			}
                	}
                }
                }
        }
        
        JSONObject jo = new JSONObject();
        try {
			jo.put("is_member", tf);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		resp.getWriter().print(jo);
		//}
		//super.doGet(req, resp);
		
		
	}

}
