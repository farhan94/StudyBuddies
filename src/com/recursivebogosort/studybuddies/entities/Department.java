package com.recursivebogosort.studybuddies.entities;

import static com.googlecode.objectify.ObjectifyService.ofy;


import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ryan on 11/15/16.
 */


@Entity
public class Department {
    @Id
    Long id;
    @Index
    String departmentName;
    @Index
    String universityName;
    @Load
    Ref<University> universityRef;
    ArrayList<Ref<Course>> courses;

    private Department(){
        this.courses = new ArrayList<>();
    }

    public Department (String departmentName, String universityName)
    {
        this.departmentName = departmentName;
        this.universityName = universityName;
        this
        this.courses = new ArrayList<Ref<Course>>();
    }

    public Collection<Course> getCourses(){
    	return ofy().load().refs(this.courses).values();
//        return ofy().load().type(Course.class).filter("universityName", this.universityName)
  //              .filter("departmentName", this.departmentName).list();
    }
    
    public Long getID(){
    	return this.id;
    }

    public void addCourse(Ref<Course> c){
    	this.courses.add(c);
    }




}
