package com.recursivebogosort.studybuddies.entities;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;


@Entity
public class GroupMember {

	@Id protected Long id;
	protected Ref<StudyBuddiesUser> userRef;
	protected Ref<Group> groupRef;

	//Any Member Specific Settings or properties

	protected GroupMember(){}

	public GroupMember(StudyBuddiesUser user, Group group)
	{
		this.userRef = Ref.create(Key.create(StudyBuddiesUser.class, user.getId()));
		this.groupRef = Ref.create(Key.create(Group.class, group.groupName));
	}

	public StudyBuddiesUser getUser() { return userRef.getValue(); }
	public Group getGroup() { return groupRef.getValue(); }

	//Any Member Specific Functions

}

