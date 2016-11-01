package com.recursivebogosort.studybuddies;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Id;

public class Group{
	//@Id Long id;
	@Id String name;
	int currentSize;
	int maxSize;
	String university;
	String course;
	String professor;
	StudyBuddiesUser owner;
	List<StudyBuddiesUser> members;
	//list of events
	private Group(){}
	public Group(String name, int currentSize, int maxSize, String university, String course, String professor,
			StudyBuddiesUser owner) {
		super();
		this.name = name;
		this.currentSize = currentSize;
		this.maxSize = maxSize;
		this.university = university;
		this.course = course;
		this.professor = professor;
		this.owner = owner;
		this.members = new ArrayList<StudyBuddiesUser>();
		members.add(this.owner);
	}

}
