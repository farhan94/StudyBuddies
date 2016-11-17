package com.recursivebogosort.studybuddies.entities;

/**
 * Created by ryan on 11/7/16.
 */
import static com.googlecode.objectify.ObjectifyService.ofy;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.cmd.Query;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class University {
    @Id String name;
    Collection<Ref<Department>> departments;
    Collection<Ref<Course>> courses;
    Collection<Ref<StudyBuddiesUser>> students;

    private University(){}

    public University(String name)
    {

        this.name = name;
        this.courses = new ArrayList<Ref<Course>>();
        this.departments = new ArrayList<Ref<Department>>();
        this.students = new ArrayList<Ref<StudyBuddiesUser>>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Collection<Department> get_departments()
    {
        return ofy().load().type(Department.class).filter("universityName", this.name).list();
    }

    public Collection<Course> get_courses()
    {
        return ofy().load().type(Course.class).filter("universityName", this.name).list();
    }

    public Collection<StudyBuddiesUser> get_users()
    {
        return ofy().load().type(StudyBuddiesUser.class).filter("universityName", this.name).list();
    }
    public void addDept(Ref<Department> dept){
    	if (this.departments == null){
    		this.departments = new ArrayList<Ref<Department>>();
    	}
    	departments.add(dept);
    }









}
