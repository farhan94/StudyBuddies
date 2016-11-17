package com.recursivebogosort.studybuddies.entities;

/**
 * Created by ryan on 11/7/16.
 */

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class Course {
    @Id Long id;
    @Index String courseId;
    @Index String courseName;
    @Index String professor;
    @Index String universityName;
    @Index String departmentName;
    @Load Ref<University> universityRef;
    @Load Ref<Department> departmentRef;
    ArrayList<Ref<Group>> groups;

    private Course() { }

    public Course(String courseId, String courseName, String professor, String universityName, String departmentName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.professor = professor;
        this.universityName = universityName;
        this.departmentName = departmentName;
        this.universityRef = ofy().load().type(University.class).id(universityName);
        if(universityRef.getValue() == null)
        {
            University university = new University(universityName);
            ofy().save().entity(university).now();
            this.universityRef = ofy().load().entity(university);
        }
        this.departmentRef = ofy().load().type(Department.class).filter("departmentName", departmentName).first();
        if(this.departmentRef.getValue() == null)
        {
            Department department = new Department(departmentName, universityName, this.universityRef);
            ofy().save().entity(department).now();
            this.departmentRef = ofy().load().entity(department);
        }

    }

    public Long getId() { return id; }

    public String getCourseId() {
        return this.courseId;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public String getProfessor() {
        return this.professor;
    }

    public Collection<Ref<Group>> getGroupRefs(){
    	return this.groups;
    }
    public University getUniversity() { return universityRef.getValue(); }

    public void setUniversity(University university) { this.universityRef = Ref.create(Key.create(University.class, university.getName()));}

    public Group addGroup(Key<Group> groupKey)
    {
    	//Key<Course> courseKey = ofy().save().entity(course).now();
        Ref<Group> groupRef = ofy().load().key(groupKey);
        //Ref<Group> groupRef = Ref.create(groupKey);
        groups.add(groupRef);
        return groupRef.getValue();
    }

    public Collection<Group> get_courses(){
        return ofy().load().refs(groups).values();
    }
}
