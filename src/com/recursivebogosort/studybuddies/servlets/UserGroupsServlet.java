package com.recursivebogosort.studybuddies.servlets;

import static com.googlecode.objectify.ObjectifyService.ofy;
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
import com.recursivebogosort.studybuddies.entities.University;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class UserGroupsServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        String userID = req.getParameter("userID");
        StudyBuddiesUser sbu = ofy().load().type(StudyBuddiesUser.class).id(userID).getValue();
        ArrayList<Ref<GroupMember>> gmrefs = sbu.getOtherGroups();

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
        			ja.put(go.getGroup().groupJSON());
        		}
        	}
        }
        }
        if(gmrefs != null){
        //Collection<GroupMember> memberGroups = ofy().load().refs(sbu.getAllGroups()).values();
        Iterator<Ref<GroupMember>> iter = gmrefs.iterator();
        
        while(iter.hasNext()){
        	Ref<GroupMember> gmref = iter.next();
        	if(gmref != null){
        		gmref = ofy().load().ref(gmref);
        		GroupMember gm = gmref.get();
        		ja.put(gm.getGroup().groupJSON());
        	}
        }
        }
//        int i = 0;
//        for (GroupMember gm : memberGroups ) {
//        	if(gm != null){
//            ja.put(gm.getGroup().groupJSON());
//        	}
//        }
        resp.setContentType("application/json");
        resp.getWriter().print(ja);
    }


}
