package com.nts.controller;

import com.nts.entity.Course;
import com.nts.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService cs;

    @RequestMapping("showAll")
    public Map showAll(String uid) {
        Map show = cs.show(uid);
        return show;
    }

    @RequestMapping("addCourse")
    public Map addCourse(Course course, String uid) {
        course.setUserId(uid);
        Map map = cs.addCourse(course);
        Map show = cs.show(uid);
        map.put("option", show.get("courses"));
        return map;
    }

    @RequestMapping("removeCourse")
    public Map removeCourse(String uid, String id) {
        Map map = cs.removeCourse(new Course().setId(id));
        return map;
    }
}
