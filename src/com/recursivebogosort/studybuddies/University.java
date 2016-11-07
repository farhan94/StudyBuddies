package com.recursivebogosort.studybuddies;

/**
 * Created by ryan on 11/7/16.
 */

import com.googlecode.objectify.Key;
import static com.googlecode.objectify.ObjectifyService.ofy;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.impl.Keys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@ Entity
public class University {
    @Id String name;
    Collection<Key<Course>> courses;
    Collection<Key<StudyBuddiesUser>> students;

    private University(){}

    public University(String name)
    {
        this.name = name;
        this.courses = new HashSet<Key<Course>>();
        this.students = new HashSet<Key<StudyBuddiesUser>>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Collection<Course> getCourses()
    {
       return ofy().load().keys(courses).values();
    }



    public Collection<StudyBuddiesUser> getStudents()
    {
        return ofy().load().keys(students).values();
    }






}
