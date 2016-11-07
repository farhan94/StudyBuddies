package com.recursivebogosort.studybuddies;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;

import static com.googlecode.objectify.ObjectifyService.ofy;
import java.io.IOException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.PhoneNumber;
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
			System.out.println(ofy().load().keys().list());
			Ref<StudyBuddiesUser> sbuRef = ofy().load().type(StudyBuddiesUser.class).id(id);
			StudyBuddiesUser sbu = sbuRef.get();
			if(sbu != null){
				System.out.println("name: " +sbu.getName() +" phoneNumber: " +sbu.getPhoneNumber() + " email: " + sbu.getEmail() + " university: " +sbu.getUniversity() + " subscribed to email: " +sbu.isSubscribedToEmails() + " subscribed to texts: " +sbu.isSubscribedToSMS());
				//redirect to just dashboard
				//ofy().delete().entity(sbu).now();
				resp.sendRedirect("/dashboard.jsp");
			}
			else{
				//redirect to creating a user (Form for all info)
				//sbu = new StudyBuddiesUser(id, user, "a","b","c", "d", false, false);
				//ofy().save().entity(sbu).now();
				resp.sendRedirect("/register.jsp");
			}
		}

		else {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
		}
	}

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
				String name = req.getParameter("name");
				if(name.equals("") || name == null){
					name = sbu.getName();
				}
				String phoneNumber = req.getParameter("phoneNumber");
				if(phoneNumber.equals("") || phoneNumber == null){
					phoneNumber = sbu.getPhoneNumber();
				}
				String[] subs = req.getParameterValues("subscription");
				boolean emailSub = false;
				boolean smsSub = false;
				if (subs!= null){
				for(int i = 0; i< subs.length; i++){
					if(subs[i].equals("emailNotification")){
						emailSub = true;
					}
					else if(subs[i].equals("textNotification")){
						smsSub = true;
					}
				}
				}
				sbu.setName(name);
				sbu.setPhoneNumber(phoneNumber);
				sbu.setSubscribedToEmails(emailSub);
				sbu.setSubscribedToSMS(smsSub);
				ofy().save().entity(sbu).now();
				resp.sendRedirect("/settings.jsp");
			}
			else{
				String name = req.getParameter("name");
				String phoneNumber = req.getParameter("phoneNumber");
				String university = req.getParameter("university");
				String[] subs = req.getParameterValues("subscription");
				boolean emailSub = false;
				boolean smsSub = false;
				if (subs!= null){
				for(int i = 0; i< subs.length; i++){
					if(subs[i].equals("emailNotification")){
						emailSub = true;
					}
					else if(subs[i].equals("textNotification")){
						smsSub = true;
					}
				}
				}
				//Date date = new Date();
				//	     Blog blog = new Blog(user, title, content);
				sbu = new StudyBuddiesUser(user, name, phoneNumber, university, emailSub, smsSub);
				ofy().save().entity(sbu).now();

				resp.sendRedirect("/dashboard.jsp");
			}
		}
		else {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
		}
	}

}