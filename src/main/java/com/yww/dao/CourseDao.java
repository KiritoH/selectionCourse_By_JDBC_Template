package com.yww.dao;

import com.yww.entity.Course;
import com.yww.entity.Student;

import java.util.List;

public interface CourseDao {
    void insert(Course course);
    void update(Course course);
    void delete(int id);
    Course select(int id);
    List<Course> selectAll();
}
