package com.recursivebogosort.studybuddies.entities;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;


@Entity
public class GroupMember {

	@Id protected Long id;
	@Load protected Ref<StudyBuddiesUser> userRef;
	@Load protected Ref<Group> groupRef;

	//Any Member Specific Settings or properties

	protected GroupMember(){}

	public GroupMember(Ref<StudyBuddiesUser> user, Ref<Group> group)
	{
		this.userRef = user;
		this.groupRef = group;
	}

	public StudyBuddiesUser getUser() { return userRef.getValue(); }
	public Group getGroup() { return groupRef.getValue(); }

	//Any Member Specific Functions

}

