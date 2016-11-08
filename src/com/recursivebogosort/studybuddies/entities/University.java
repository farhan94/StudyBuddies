package com.recursivebogosort.studybuddies.entities;

/**
 * Created by ryan on 11/7/16.
 */

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Collection;
import java.util.HashSet;

@ Entity
public class University {
    @Id String name;
    Collection<Ref<Course>> courses;
    Collection<Ref<StudyBuddiesUser>> students;

    private University(){}

    public University(String name)
    {
        this.name = name;
        this.courses = new HashSet<Ref<Course>>();
        this.students = new HashSet<Ref<StudyBuddiesUser>>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }







}
