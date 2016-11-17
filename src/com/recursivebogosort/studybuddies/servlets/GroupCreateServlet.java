package com.recursivebogosort.studybuddies.servlets;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.recursivebogosort.studybuddies.entities.Course;
import com.recursivebogosort.studybuddies.entities.Group;
import com.recursivebogosort.studybuddies.entities.StudyBuddiesUser;
import com.recursivebogosort.studybuddies.entities.University;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by ryan on 11/8/16.
 */
public class GroupCreateServlet extends HttpServlet {

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
          resp.sendRedirect("/groupcreate.jsp");
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
        Ref<University> universityRef = sbu.getUniversityRef();

        //Group Fields
        String groupName = req.getParameter("group_name");
        String groupDescription = req.getParameter("group_description");
        int maxSize = Integer.parseInt(req.getParameter("max_size"));
        Boolean joinByRequest = Boolean.getBoolean(req.getParameter("join_by_request"));

        //Course Field
        String courseId = req.getParameter("course_id");
        String courseName = req.getParameter("course_name");
        String professor = req.getParameter("professor");

        Ref<Course> courseRef = ofy().load().type(Course.class).filter("courseId ==", courseId).first();

        if(courseRef.get() == null)
        {
            Course course = new Course(courseId, courseName, professor, sbuRef.get().getUniversityRef());
            System.out.println(course.getCourseName() + " " +course.getUniversity().getName());
            Key<Course> courseKey = ofy().save().entity(course).now();
            courseRef = ofy().load().key(courseKey);
            Course cc = courseRef.getValue();
            System.out.println(cc.getId());
         //   courseRef = Ref.create(courseKey);
           // System.out.println(courseRef.getValue().getUniversity().getName());
        }
        
        Group group = Group.CreateGroup(groupName, maxSize, courseRef.getValue(), joinByRequest, sbu);

        resp.sendRedirect("/dashboard.jsp");

    }


}
