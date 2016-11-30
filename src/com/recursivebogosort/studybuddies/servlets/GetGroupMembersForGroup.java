package com.recursivebogosort.studybuddies.servlets;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.googlecode.objectify.Ref;
import com.recursivebogosort.studybuddies.entities.Group;
import com.recursivebogosort.studybuddies.entities.GroupMember;
import com.recursivebogosort.studybuddies.entities.GroupOwner;

public class GetGroupMembersForGroup extends HttpServlet{

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//long id = ofy().load().type(Course.class).first().getValue().getId();
		String cIDString = req.getParameter("groupID");
		long cID = Long.parseLong(cIDString);
		Ref<Group> groupRef = ofy().load().type(Group.class).id(cID);
		Group group = ofy().load().ref(groupRef).getValue();
		JSONArray ar = new JSONArray();
		JSONObject jo = new JSONObject();
		GroupMember owner = group.getOwner();
		if(owner != null){
			try {
				jo.put("name", owner.getUser().getName());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ar.put(jo);
		}
		ArrayList<Ref<GroupMember>> gmRefs = group.getMembers();
		for(int k = 0; k < gmRefs.size(); k++){
			Ref<GroupMember> gmRef = gmRefs.get(k);
			gmRef = ofy().load().ref(gmRef);
			GroupMember gm = gmRef.get();
			 jo = new JSONObject();
			try {
				jo.put("name", gm.getUser().getName());
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
