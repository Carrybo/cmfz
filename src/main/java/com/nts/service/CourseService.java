package com.nts.service;

import com.nts.entity.Course;

import java.util.Map;

public interface CourseService {
    Map addCourse(Course course);

    Map removeCourse(Course course);

    Map show(String uid);
}
