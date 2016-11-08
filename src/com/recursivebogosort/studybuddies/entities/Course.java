package com.recursivebogosort.studybuddies.entities;

/**
 * Created by ryan on 11/7/16.
 */

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Collection;
import java.util.HashSet;

@Entity
public class Course {
    @Id Long id;
    String courseId;
    String courseName;
    String professor;
    Ref<University> universityRef;
    Collection<Ref<Group>> groups;

    private Course() { }

    public Course(String courseId, String courseName, String professor, Ref<University> universityRef) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.professor = professor;
        this.universityRef = universityRef;
        this.groups = new HashSet<Ref<Group>>();
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

    public University getUniversity() { return universityRef.getValue(); }

    public void setUniversity(University university) { this.universityRef = Ref.create(Key.create(University.class, university.getName()));}

    public Group addGroup(Key<Group> groupKey)
    {
        Ref<Group> groupRef = Ref.create(groupKey);
        groups.add(groupRef);
        return groupRef.getValue();
    }
}
