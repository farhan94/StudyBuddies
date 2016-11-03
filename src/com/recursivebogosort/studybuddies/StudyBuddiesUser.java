package com.recursivebogosort.studybuddies;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class StudyBuddiesUser {
	@Id String id;
    User user;
    String name;
    String email;
    String phoneNumber;
    String university;
    boolean subscribedToEmails;
    boolean subscribedToSMS;
    //list for UserGroups
       
    private StudyBuddiesUser() {}
    public StudyBuddiesUser(User user, String name, /*String email,*/ String phoneNumber, String university,  boolean subscribedToEmails, boolean subscribedToSMS) {
        this.id = user.getUserId();
    	this.user= user;
        this.name = name;
        this.email = user.getEmail();
        this.phoneNumber = phoneNumber;
        this.university = university;
        this.subscribedToEmails = subscribedToEmails;
        this.subscribedToSMS = subscribedToSMS;
    }
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public boolean isSubscribedToEmails() {
		return subscribedToEmails;
	}
	public void setSubscribedToEmails(boolean subscribedToEmails) {
		this.subscribedToEmails = subscribedToEmails;
	}
	public boolean isSubscribedToSMS() {
		return subscribedToSMS;
	}
	public void setSubscribedToSMS(boolean subscribedToSMS) {
		this.subscribedToSMS = subscribedToSMS;
	}
	public User getUser() {
        return user;
    }
    public boolean hasReqInfo(){
    	if(user == null || name == null || email == null || phoneNumber == null || university == null){
    		return false;
    	}
    	return true;
    }
}

