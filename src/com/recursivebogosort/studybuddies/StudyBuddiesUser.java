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
    public StudyBuddiesUser(String id, User user, String name, String email, String phoneNumber, String university,  boolean subscribedToEmails, boolean subscribedToSMS) {
        this.id = id;
    	this.user= user;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.university = university;
        this.subscribedToEmails = subscribedToEmails;
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

