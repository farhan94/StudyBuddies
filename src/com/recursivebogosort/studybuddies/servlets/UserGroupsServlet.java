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
import com.recursivebogosort.studybuddies.entities.StudyBuddiesUser;
import com.recursivebogosort.studybuddies.entities.University;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by ryan on 11/17/16.
 */
public class UserGroupsServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        String userID = req.getParameter("userID");
        StudyBuddiesUser sbu = ofy().load().type(StudyBuddiesUser.class).id(userID).getValue();
        Collection<GroupMember> memberGroups = ofy().load().refs(sbu.getAllGroups()).values();

        JSONArray ja = new JSONArray();
        int i = 0;
        for (GroupMember gm : memberGroups ) {
            ja.put(gm.getGroup().groupJSON());
        }
        resp.setContentType("application/json");
        resp.getWriter().print(ja);
    }


}
