package com.yww.dao.impl;

import com.yww.dao.StudentDao;
import com.yww.entity.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentDaoImpl implements StudentDao {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;


    public void insert(Student stu) {
        String sql = "insert into student(name,sex,born) values(?,?,?)";
        jdbcTemplate.update(sql,stu.getName(),stu.getSex(),stu.getBorn());

    }

    public void update(Student stu) {

        String sql = "update student set name=? ,sex=?,born=? where id=? ";
        jdbcTemplate.update(sql,stu.getName(),stu.getSex(),stu.getBorn(),stu.getId());
    }

    public void delete(int id) {

        String sql = "delete from student where id = ?";
        jdbcTemplate.update(sql,id);
    }

    public Student findOne(int id) {
        String sql = "select * from student where id = ?";
        Student student = jdbcTemplate.queryForObject(sql,new StudentRowMapper(),id);
        return student;
    }

    public List<Student> findAll() {
        String sql = "select * from student where id = ?";

        List<Student> students = jdbcTemplate.query(sql,new StudentRowMapper());
        return students;
    }



    //内部类
    //尽量将其私有,可以隐藏实现细节
    //会看到使用匿名内部类的方式进行映射造成了代码的重用性过低,故此,可以直接自己声明一个
    private class StudentRowMapper implements RowMapper<Student> {

        //====自我推测===并没有进行传值,故推断该方法会自动执行,而ResultSet对象应该是
        // 一个状态对象,在某个域中可以找到
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
            Student student = new Student();
            student.setId(resultSet.getInt("id"));
            student.setBorn(resultSet.getDate("born"));
            student.setName(resultSet.getString("name"));
            student.setSex(resultSet.getString("sex"));
            return student;
        }
    }
}
