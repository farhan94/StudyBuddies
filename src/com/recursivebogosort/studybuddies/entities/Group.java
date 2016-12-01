package com.recursivebogosort.studybuddies.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

@Entity
public class Group{
	@Id Long id;
    String groupName;
    String groupDescription = "";
    int currentSize;            // Current number of members not including the owner
	int maxSize;                // Max number of members not including the owner
    Boolean joinByRequest;      // open to all members to join or needs request

    @Load Ref<Course> course;

    @Load Ref<GroupOwner> ownerRef;
	ArrayList<Ref<Event>> events;
	ArrayList<Ref<GroupMember>> members;
    ArrayList<Ref<GroupJoinRequest>> joinRequest;

    private Group(){}

	public Group(String name, String description, int maxSize, Course course, boolean joinByRequest,
			StudyBuddiesUser owner) {
		super();
		this.groupName = name;
		this.groupDescription = description;
		this.currentSize = 1;
		this.maxSize = maxSize;
		this.joinByRequest = joinByRequest;
		this.events = new ArrayList<Ref<Event>>();
		this.members = new ArrayList<Ref<GroupMember>>();
	//	this.course = Ref.create(Key.create(Course.class, course.getId()));
    }
	
	public Group(String name, String description, int maxSize, boolean joinByRequest, boolean isTest) {
		super();
		this.groupName = name;
		this.groupDescription = description;
		this.currentSize = 1;
		this.maxSize = maxSize;
		this.joinByRequest = joinByRequest;
		//this.events = new ArrayList<Ref<Event>>();
		//this.members = new ArrayList<Ref<GroupMember>>();
	//	this.course = Ref.create(Key.create(Course.class, course.getId()));
    }

	public String getGroupDescription(){
		return this.groupDescription;
	}
	public boolean containsSBU(Ref<StudyBuddiesUser> sbuRef){
		if(members != null){
			Iterator<Ref<GroupMember>> i = members.iterator();
			while(i.hasNext()){
				Ref<GroupMember> gmRef = i.next();
				gmRef = ofy().load().ref(gmRef);
				GroupMember gm = gmRef.get();
				if(gm.getUser().getId() == sbuRef.getValue().getId()){
					return true;
				}
				
			}
		}
		if(ownerRef != null){
		if(ownerRef.get().getUser().getId() == sbuRef.get().getId()){
			return true;
		}
		}
		return false;
	}
	
	public boolean removeSBU(Ref<StudyBuddiesUser> sbuRef){
		if(members != null){
			Iterator<Ref<GroupMember>> i = members.iterator();
			while(i.hasNext()){
				Ref<GroupMember> gmRef = i.next();
				gmRef = ofy().load().ref(gmRef);
				GroupMember gm = gmRef.get();
				if(gm.getUser().getId() == sbuRef.getValue().getId()){
					StudyBuddiesUser sbu = sbuRef.get();
					sbu.leaveGroup(gm.getGroup());
					ofy().save().entity(sbu).now();
					i.remove();
					ofy().delete().entity(gm);
					return true;
				}
				
			}
		}
		if(ownerRef != null){
			if(ownerRef.get().getUser().getId() == sbuRef.get().getId()){
				StudyBuddiesUser sbu2 = ownerRef.get().getUser();
				sbu2.leaveGroup(this);
				ofy().save().entity(sbu2).now();
				ofy().delete().entity(ownerRef.get());
				ownerRef = null;
				return true;
			}}
		return false;
	}
	public Long getId(){ return id;}
	public boolean isJoinByRequest(){
		return this.joinByRequest;
	}
    public String getGroupName() { return groupName; }
    public void setGroupName(String name) { groupName = name; }

    public int getCurrentSize(){ return currentSize; }
    public void setCurrentSize(int i){  currentSize = i; }
    public int getMaxSize() { return maxSize; }
    public int trySetMaxSize(int size) { return maxSize = currentSize <= size ? size : maxSize; }

    public GroupOwner getOwner() { if(ownerRef == null){
    	return null;
    	}
    return ownerRef.getValue(); }
    
    public void setOwner(Key<GroupOwner> groupOwnerKey) { ownerRef = ofy().load().key(groupOwnerKey); }
    
    public ArrayList<Ref<GroupMember>> getMembers() {
    	if(members == null){
    		this.members = new ArrayList<Ref<GroupMember>>();
    	}
    	
    	return members; 
    }
    public void addMember(Key<GroupMember> groupMemberKey) {
    	if(members == null){
    		this.members = new ArrayList<Ref<GroupMember>>();
    	}
    	members.add(ofy().load().key(groupMemberKey)); 
    	}
    
    public void RequestJoin(StudyBuddiesUser user)
    {
//        if(joinByRequest)
//        {
//            GroupJoinRequest req = new GroupJoinRequest(user, this);
//            Key<GroupJoinRequest> reqKey = ofy().save().entity(req).now();
//            Ref<GroupJoinRequest> reqRef;
//            if(reqKey != null) reqRef = Ref.create(reqKey);
//        }
    }

    public void AcceptJoinRequest(GroupJoinRequest req)
    {
//        this.joinRequest.remove(req);
//        GroupMember groupJoined = new GroupMember(req.getUser(), req.getGroup());
//        Key<GroupMember> groupJoinedKey = ofy().save().entity(groupJoined).now();
//        this.members.add(Ref.create(groupJoinedKey));
//        req.getUser().joinRequestAccepted(req, groupJoined);
//        ofy().save().entity(req.getUser());
//        ofy().save().entity(this);
//        ofy().delete().entity(req);
    }

    public Collection<Ref<Event>> getEventRefs(){
    	return this.events;
    }
    public void DenyJoinRequest(GroupJoinRequest req)
    {
        //TODO
    }

    public static Group CreateGroup(String name, String description, int maxSize, Course course, boolean joinByRequest, StudyBuddiesUser user)
    {
        Group group = new Group(name, description, maxSize, course, joinByRequest, user);
        Key<Group> groupKey = ofy().save().entity(group).now();
        GroupOwner groupOwner =  new GroupOwner(ofy().load().type(StudyBuddiesUser.class).id(user.getId()), ofy().load().key(groupKey));
        Key<GroupOwner> groupOwnerKey = ofy().save().entity(groupOwner).now();
        user.addMyGroup(ofy().load().key(groupOwnerKey));
        group.setOwner(groupOwnerKey);
        ofy().save().entity(group).now();
        course.addGroup(groupKey);
        ofy().save().entity(user).now();
       // ofy().save().entity(groupOwner).now();
        ofy().save().entity(course).now();
        return group;
    }
    
    //ADDS EVENT TO THE GROUP
	public void addEvent(Key<Event> eventKey) {
    	if(events == null){
    		this.events = new ArrayList<Ref<Event>>();
    	}
    	Ref<Event> eventRef = ofy().load().key(eventKey);
		events.add(eventRef);
		this.addEventToGroupMembers(eventRef);
		// TODO Auto-generated method stub
		
	}
	
	//THE FOLLOWING METHOD ADDS AN EVENTREF TO A STUDYBUDDIES USER
    public void addEventToGroupMembers(Ref<Event> eventRef){
    	if(members == null){
    		members = new ArrayList<Ref<GroupMember>>();
    	}
    	Iterator i = members.iterator();
    	while(i.hasNext()){
    		Ref<GroupMember> memberRef = (Ref<GroupMember>) i.next();
    		GroupMember member = ofy().load().ref(memberRef).get();
    		//member.getUser().addEvent(eventRef);
    		StudyBuddiesUser sbum = member.getUser();
    		sbum.addEvent(eventRef);
    		ofy().save().entity(sbum).now();
    	}
    	if(ownerRef != null){
    	StudyBuddiesUser sbuo = ownerRef.get().getUser();
    	sbuo.addEvent(eventRef);
    	ofy().save().entity(sbuo).now();
    	}
    	//iterate through every group member and owner and add the event to their event list
    }

    public JSONObject groupJSON()
	{
		JSONObject jo = new JSONObject();
		try {
			jo.put("uid", getId().toString());
			jo.put("name", getGroupName());
			jo.put("size", getCurrentSize());
			jo.put("purpose", getGroupDescription());
			jo.put("is_member", true);
			jo.put("max", this.maxSize);
			jo.put("icon_url","google.com");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jo;
	}

}
