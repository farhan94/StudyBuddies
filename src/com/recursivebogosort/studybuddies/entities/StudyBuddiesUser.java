package com.recursivebogosort.studybuddies.entities;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

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

    @Load Ref<University> universityRef;
    ArrayList<Ref<GroupMember>> groupsJoined;
    ArrayList<Ref<GroupJoinRequest>> groupsRequested;
    ArrayList<Ref<GroupOwner>> MyGroups;
    ArrayList<Ref<Event>> upcomingEvents;

    private StudyBuddiesUser() {}
    public StudyBuddiesUser(User user, String name, /*String email,*/ String phoneNumber, String university, Ref<University> universityRef, boolean subscribedToEmails, boolean subscribedToSMS) {
        this.id = user.getUserId();
    	this.user= user;
        this.name = name;
        this.email = user.getEmail();
        this.phoneNumber = phoneNumber;
        this.university = university;
        this.subscribedToEmails = subscribedToEmails;
        this.subscribedToSMS = subscribedToSMS;
		this.universityRef = universityRef;
		this.groupsJoined = new ArrayList<Ref<GroupMember>>();
		this.groupsRequested = new ArrayList<Ref<GroupJoinRequest>>();
		this.MyGroups = new ArrayList<Ref<GroupOwner>>();
		this.upcomingEvents = new ArrayList<Ref<Event>>();
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
	public Ref<University> getUniversityRef() { return this.universityRef; }
	public void setUniversityRef(Ref<University> universityRef) { this.universityRef = universityRef; }
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

    public void addJoinRequest(GroupJoinRequest request) {
        //TODO
    }

    public void joinRequestAccepted(GroupJoinRequest request, GroupMember groupJoined ) {
        //TODO
    }

    public void joinRequestDenied(GroupJoinRequest request) {
        //TODO
    }

    public void addMyGroup(Ref<GroupOwner> myGroup) {
    	if(MyGroups == null){
    		MyGroups = new ArrayList<Ref<GroupOwner>>();
    	}
    	MyGroups.add(myGroup);
        //TODO
    }
    public ArrayList<Ref<GroupOwner>> getMyGroups(){
    	return this.MyGroups;
    }
    public void addEvent(Ref<Event> event){
    	if(upcomingEvents == null){
    		upcomingEvents = new ArrayList<Ref<Event>>();
    	}
    	upcomingEvents.add(event);
    }
    public ArrayList<Ref<Event>> getEvents(){
    	if (this.upcomingEvents == null){
    		this.upcomingEvents = new ArrayList<Ref<Event>>();
    	}
    	return this.upcomingEvents;
    }
    public void joinGroup(Ref<GroupMember> groupToJoin){
    	if(groupsJoined == null){
    		groupsJoined = new ArrayList<Ref<GroupMember>>();
    	}
    	groupsJoined.add(groupToJoin);
    	//add events to event list;
    }
    
    public ArrayList<Ref<GroupMember>> getAllGroups(){
    	if(groupsJoined != null && MyGroups == null){
    		return null;
    	}
    	else{
    		ArrayList<Ref<GroupMember>> result = new ArrayList<Ref<GroupMember>>();
    		if(MyGroups == null){
        		return this.groupsJoined;
        	}
    		else if (groupsJoined == null){
    			Iterator i = this.MyGroups.iterator();
    			while(i.hasNext()){
    				Ref<GroupMember> m  = (Ref<GroupMember>) i.next();
    				result.add(m);
    			}
    			return result;
    		}
    		else{
    			Iterator i = this.MyGroups.iterator();
    			while(i.hasNext()){
    				Ref<GroupMember> m  = (Ref<GroupMember>) i.next();
    				result.add(m);
    			}
    			i = this.groupsJoined.iterator();
    			while(i.hasNext()){
    				Ref<GroupMember> m  = (Ref<GroupMember>) i.next();
    				result.add(m);
    			}
    			return result;
    		}
    	}
    	
    }
    
    public GroupMember getOneGroup(){
    	if(MyGroups == null){
    		return null;
    	}
    	ArrayList<Ref<GroupOwner>> a = (ArrayList<Ref<GroupOwner>>)MyGroups;
    	Ref<GroupOwner> re = a.get(0);
    	Ref<GroupOwner> g = ofy().load().ref(re);  //ALWAYS NEED TO MANUALLY LOAD WHEN GOING THROUGH THE LIST, LOAD THE REF, THEN DEREF
    	return g.getValue();
//    	Iterator<Ref<GroupOwner>> i = MyGroups.iterator();
//    	System.out.println("ASDFASDFAP");
//    	GroupMember gm = i.next().getValue();
//    	//Group gr = gm.getGroup();
//    	if (gm == null){
//    		System.out.println("NULL RETURNED GETONEGROUP");
//    		return null;
//    	}
//    	return gm;
    }

    public boolean leaveGroup(Group g){
    	ArrayList<Ref<GroupMember>> aList = this.getOtherGroups();
    	if(aList != null){
    	Iterator<Ref<GroupMember>> i = aList.iterator();
    	while(i.hasNext()){
    		Ref<GroupMember> gmRef = i.next();
    		gmRef = ofy().load().ref(gmRef);
    		GroupMember gm = gmRef.get();
    		if(gm != null){
    		if(g.getId() == gm.getGroup().getId()){
    			i.remove();
    			return true;
    		}
    		}
    		
    	}}
    	ArrayList<Ref<GroupOwner>> bList = this.getMyGroups();
    	if(bList != null){
    		Iterator<Ref<GroupOwner>> it = bList.iterator();
        	while(it.hasNext()){
        		Ref<GroupOwner> goRef = it.next();
        		goRef = ofy().load().ref(goRef);
        		GroupOwner go = goRef.get();
        		if(go != null){
        		if(g.getId() == go.getGroup().getId()){
        			it.remove();
        			return true;
        		}
        		}
        		
        	}
    	}
    	return false;
    }
    
    public ArrayList<Ref<GroupMember>> getOtherGroups(){
    	return this.groupsJoined;
    }



}

