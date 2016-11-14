package com.recursivebogosort.studybuddies.servlets;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.recursivebogosort.studybuddies.entities.Course;
import com.recursivebogosort.studybuddies.entities.Event;
import com.recursivebogosort.studybuddies.entities.Group;
import com.recursivebogosort.studybuddies.entities.StudyBuddiesUser;
import com.recursivebogosort.studybuddies.entities.University;

public class EventCreateServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        if (user == null) {
            resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        }

        Ref<StudyBuddiesUser> sbuRef = ofy().load().type(StudyBuddiesUser.class).id(user.getUserId());

        if(sbuRef.get() == null) {
            resp.sendRedirect("/studybuddies");
        }
        else
          resp.sendRedirect("/eventcreate.jsp");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        if (user == null) {
            resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        }

        Ref<StudyBuddiesUser> sbuRef = ofy().load().type(StudyBuddiesUser.class).id(user.getUserId());

        if(sbuRef.get() == null) {
            resp.sendRedirect("/register.jsp");
        }

        StudyBuddiesUser sbu = sbuRef.get();
        //Ref<University> universityRef = sbu.getUniversityRef();

        //Group Fields
        String eventName = req.getParameter("event_name");
        String eventDescription = req.getParameter("event_description");
        String groupID = req.getParameter("group");
        String date = req.getParameter("date");
        Date date2 = null;
        //int maxSize = Integer.parseInt(req.getParameter("max_size"));
        //Boolean joinByRequest = Boolean.getBoolean(req.getParameter("join_by_request"));

        //Course Field
        //String courseId = req.getParameter("course_id");
        //String courseName = req.getParameter("course_name");
        //String professor = req.getParameter("professor");

        Ref<Group> groupRef = ofy().load().type(Group.class).id(Long.parseLong(groupID));

        if(groupRef.get() == null)
        {
        	System.out.println("ERROR IN GETTING GROUP");
        	//maybe redirect to error page
        	return;
        }
        
        Event event = Event.createEvent(eventName, eventDescription, groupRef.get(), date2);
        resp.sendRedirect("/dashboard.jsp");

    }
}
