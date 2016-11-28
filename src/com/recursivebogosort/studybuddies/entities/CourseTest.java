package com.recursivebogosort.studybuddies.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class CourseTest {

	@Test
	public void test() {
		Course course = new Course("a", "b", "c", "d", "Something", true);
		System.out.println(course.courseId);
		System.out.println("hi");
	}

}
