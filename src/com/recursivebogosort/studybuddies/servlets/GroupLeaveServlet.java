package com.recursivebogosort.studybuddies.servlets;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.recursivebogosort.studybuddies.entities.Group;
import com.recursivebogosort.studybuddies.entities.GroupMember;
import com.recursivebogosort.studybuddies.entities.StudyBuddiesUser;

public class GroupLeaveServlet extends HttpServlet {
//	static {
//	ObjectifyService.register(StudyBuddiesUser.class);
//
//}

public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	// We have one entity group per Guestbook with all Greetings residing
	// in the same entity group as the Guestbook to which they belong.
	// This lets us run a transactional ancestor query to retrieve all
	// Greetings for a given Guestbook.  However, the write rate to each
	// Guestbook should be limited to ~1/second.
	if (user != null) {
		String id = user.getUserId();
		Ref<StudyBuddiesUser> sbuRef = ofy().load().type(StudyBuddiesUser.class).id(id);
		StudyBuddiesUser sbu = sbuRef.get();
		if(sbu != null){
			//System.out.println("name: " +sbu.getName() +" phoneNumber: " +sbu.getPhoneNumber() + " email: " + sbu.getEmail() + " university: " +sbu.getUniversity() + " subscribed to email: " +sbu.isSubscribedToEmails() + " subscribed to texts: " +sbu.isSubscribedToSMS());
			//redirect to just dashboard
			//ofy().delete().entity(sbu).now();
			long groupID = Long.parseLong(req.getParameter("groupID"));
			Ref<Group> groupRef = ofy().load().type(Group.class).id(groupID);
			Group group = groupRef.get();
			if(group != null){
				if(!group.containsSBU(sbuRef)){
					resp.getWriter().print("false");
					return;
				}
				boolean tf = group.removeSBU(sbuRef);
				
				if (tf == true){
					group.setCurrentSize(group.getCurrentSize() - 1);
					ofy().save().entity(group).now();
//					if(group.getCurrentSize() <= 0){
//						ofy().delete().entity(group);
//					}
					resp.getWriter().print("True");
					
				}
				else{
					resp.getWriter().print("false");
				}
				}
			
			}
		}
	else {
		resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
	}
}

}
