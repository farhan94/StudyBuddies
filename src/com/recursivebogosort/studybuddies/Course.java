package com.recursivebogosort.studybuddies;

/**
 * Created by ryan on 11/7/16.
 */

import static com.googlecode.objectify.ObjectifyService.ofy;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
public class Course {
    @Id private String courseId;
    private String courseName;
    private String professor;
    private Key<University> universityKey;
    private Collection<Key<Group>> groups;

    private Course() { }

    public Course(String courseId, String courseName, String professor, University university) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.professor = professor;
        this.universityKey = Key.create(University.class, university.getName());
        this.groups = new HashSet<Key<Group>>();
    }

    public String getCourseId() {
        return this.courseId;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public String getProfessor() {
        return this.professor;
    }

    public University getUniversity()
    {
        return ofy().load().key(universityKey).getValue();
    }

    public void setUniversity(University university)
    {
        this.universityKey = Key.create(University.class, university.getName());
    }

    public Collection<Group> getGroups()
    {
        return ofy().load().keys(groups).values();
    }



}
