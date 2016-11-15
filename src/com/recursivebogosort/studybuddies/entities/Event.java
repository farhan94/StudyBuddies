package com.recursivebogosort.studybuddies.entities;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

@Entity
public class Event {
	
	@Id Long id;
    String eventName;
    String eventDiscription;
    // int currentSize;            // Current number of members not including the owner
	// int maxSize;                // Max number of members not including the owner
    String date;
	@Load Ref<Group> group;
	
	 private Event(){}

		private Event(String name, String description, Group group, String date) {
			super();
			this.eventName = name;
			this.eventDiscription = description;
			this.date = date; //yyyy -mm -dd
			// this.eventSize = 0;
			// this.maxSize = maxSize;
		//	this.course = Ref.create(Key.create(Course.class, course.getId()));
	    }


		public Long getId(){ return id;}

	    public String getEventName() { return eventName; }
	    public void setEventName(String name) { eventName = name; }

//	    public GroupOwner getOwner() { return ownerRef.getValue(); }
//	    public void setOwner(Key<GroupOwner> groupOwnerKey) { ownerRef = ofy().load().key(groupOwnerKey); }



	    public static Event createEvent(String name, String description, Group group, String date)
	    {
	        Event event = new Event(name, description, group, date);
	        //event.addAllGroupMembersToEvent();
	        Key<Event> eventKey = ofy().save().entity(event).now();
	        group.addEvent(eventKey);
	       // ofy().save().entity(user).now();
	       // ofy().save().entity(groupOwner).now();
	        ofy().save().entity(group).now();
	        return event;
	    }

}
