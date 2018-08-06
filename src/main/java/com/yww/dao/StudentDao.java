package com.yww.dao;

import com.yww.entity.Student;

import java.util.List;

public interface StudentDao {
    //添加学生
    public void insert(Student stu);
    //修改学生信息
    public void update(Student stu);
    //删除学生
    public void delete(int id);
    //查找单个学生
    public Student findOne(int id);
    //查找全部学生
    public List<Student> findAll();
}
