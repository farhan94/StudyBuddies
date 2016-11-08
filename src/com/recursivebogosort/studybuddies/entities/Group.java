package com.recursivebogosort.studybuddies.entities;

import java.util.Collection;

import static com.googlecode.objectify.ObjectifyService.ofy;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

@Entity
public class Group{
	@Id Long id;
    String groupName;
    String groupDiscription;
    int currentSize;            // Current number of members not including the owner
	int maxSize;                // Max number of members not including the owner

    Boolean joinByRequest;      // open to all members to join or needs request

    @Load Ref<Course> course;

	Ref<GroupOwner> ownerRef;
	Collection<Ref<GroupMember>> members;
    Collection<Ref<GroupJoinRequest>> joinRequest;

    private Group(){}

	private Group(String name, int maxSize, Course course, boolean joinByRequest,
			StudyBuddiesUser owner) {
		super();
		this.groupName = name;
		this.currentSize = 0;
		this.maxSize = maxSize;
		this.course = Ref.create(Key.create(Course.class, course.getId()));
    }


	public Long getId(){ return id;}

    public String getGroupName() { return groupName; }
    public void setGroupName(String name) { groupName = name; }

    public int getCurrentSize(){ return currentSize; }
    public int getMaxSize() { return maxSize; }
    public int trySetMaxSize(int size) { return maxSize = currentSize <= size ? size : maxSize; }

    public GroupOwner getOwner() { return ownerRef.getValue(); }
    public void setOwner(Key<GroupOwner> groupOwnerKey) { ownerRef = Ref.create(groupOwnerKey); }


    public void RequestJoin(StudyBuddiesUser user)
    {
        if(joinByRequest)
        {
            GroupJoinRequest req = new GroupJoinRequest(user, this);
            Key<GroupJoinRequest> reqKey = ofy().save().entity(req).now();
            Ref<GroupJoinRequest> reqRef;
            if(reqKey != null) reqRef = Ref.create(reqKey);
        }
    }

    public void AcceptJoinRequest(GroupJoinRequest req)
    {
        this.joinRequest.remove(req);
        GroupMember groupJoined = new GroupMember(req.getUser(), req.getGroup());
        Key<GroupMember> groupJoinedKey = ofy().save().entity(groupJoined).now();
        this.members.add(Ref.create(groupJoinedKey));
        req.getUser().joinRequestAccepted(req, groupJoined);
        ofy().save().entity(req.getUser());
        ofy().save().entity(this);
        ofy().delete().entity(req);
    }


    public void DenyJoinRequest(GroupJoinRequest req)
    {
        //TODO
    }

    public static Group CreateGroup(String name, int maxSize, Course course, boolean joinByRequest, StudyBuddiesUser user)
    {
        Group group = new Group(name, maxSize, course, joinByRequest, user);
        Key<Group> groupKey = ofy().save().entity(group).now();
        GroupOwner groupOwner =  new GroupOwner(user, group);
        Key<GroupOwner> groupOwnerKey = ofy().save().entity(groupOwner).now();
        user.addMyGroup(groupOwner);
        group.setOwner(groupOwnerKey);
        course.addGroup(groupKey);
        ofy().save().entity(user);
        ofy().save().entity(groupOwner);
        ofy().save().entity(course);
        return group;
    }

}
