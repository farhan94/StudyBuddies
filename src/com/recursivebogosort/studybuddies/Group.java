package com.recursivebogosort.studybuddies;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Id;

public class Group{
	@Id String groupName;
	int currentSize;
	int maxSize;
	Course course;
	StudyBuddiesUser owner;
	List<StudyBuddiesUser> members;
	//list of events
	private Group(){}

	public Group(String name, int maxSize, Course course,
			StudyBuddiesUser owner) {
		super();
		this.groupName = name;
		this.currentSize = 0;
		this.maxSize = maxSize;
		this.course = course;
		this.owner = owner;
		this.members = new ArrayList<StudyBuddiesUser>();
		members.add(this.owner);
	}


	public set

}
