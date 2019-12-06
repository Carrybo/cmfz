package com.nts.service.impl;

import com.nts.dao.CourseDao;
import com.nts.entity.Course;
import com.nts.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;

    @Override
    public Map addCourse(Course course) {
        course.setId(UUID.randomUUID().toString()).setCreateDate(new Date()).setStatus("1");
        courseDao.insertSelective(course);
        Map map = new HashMap();
        map.put("status", "200");
        return map;
    }

    @Override
    public Map removeCourse(Course course) {
        courseDao.deleteByPrimaryKey(course);
        Map map = new HashMap();
        map.put("status", "200");
        return map;
    }

    @Override
    public Map show(String uid) {
        // 查询出必修课
        List<Course> select = courseDao.select(new Course().setStatus("0"));
        List<Course> courses = courseDao.select(new Course().setUserId(uid));
        // 将必修课添加进查询出的用户课程中
        select.forEach(course -> {
            courses.add(course);
        });
        Map map = new HashMap();
        map.put("status", "200");
        map.put("courses", courses);
        return map;
    }
}
