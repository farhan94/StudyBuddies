package com.recursivebogosort.studybuddies;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;

import static com.googlecode.objectify.ObjectifyService.ofy;
import java.io.IOException;
import javax.servlet.http.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

 
public class StudyBuddiesServlet extends HttpServlet {
	static {
        ObjectifyService.register(StudyBuddiesUser.class);
    }
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
              throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
// 
        if (user != null) {
        	String id = user.getUserId();
        	Ref<StudyBuddiesUser> sbuRef = ofy().load().type(StudyBuddiesUser.class).id(id);
        	StudyBuddiesUser sbu = sbuRef.get();
        	if(sbu != null){
        		//redirect to just dashboard
        		//ofy().delete().entity(sbu).now();
        		resp.sendRedirect("/dashboard.jsp");
        	}
        	else{
        		//redirect to creating a user (Form for all info)
        		//sbu = new StudyBuddiesUser(id, user, "a","b","c", "d", false, false);
        		//ofy().save().entity(sbu).now();
        		resp.sendRedirect("/homepage.jsp");
        	}
        }
        
        else {
            resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        }
    }
}