import com.yww.entity.Student;
import org.junit.runner.RunWith;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)   //@RunWith就是一个运行器,让测试运行于Spring测试环境
@ContextConfiguration(value = "classpath:spring.xml")  //此处相当于获得上下文对象,更为方便
public class Test {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @org.junit.Test
    public void testExecute(){

        jdbcTemplate.execute("create table user1(id int,name varchar(20))");
    }

    //update用法1
    @org.junit.Test
    public void testUpdate(){

        String sql = "insert into student(name,sex) value(?,?)";
        jdbcTemplate.update(sql,new Object[]{"东方未明","男"});
    }

    //update用法2
    @org.junit.Test
    public void testUpdate2(){

        String sql = "update student set name=?,sex=? where id=?";
        jdbcTemplate.update(sql,"风吹雪","女",1);
    }

    //batchUpdate用法1
    @org.junit.Test
    public void testBatchUpdate(){
        //定义sql语句组
        String[] sqls = {
                "insert into student(name,sex) value('东方未明','男')",
                "insert into student(name,sex) value('秦红殇','女')",
                "insert into student(name,sex) value('沈湘芸','女')",
                "update student set name='风吹雪',sex='女' where id=1"
        };
        jdbcTemplate.batchUpdate(sqls);
    }


    //batchUpdate用法2
    @org.junit.Test
    public void testBatchUpdate2(){
        String sql = "insert into selection(student,course) values(?,?)";
        //构造Object数组
        List<Object[]> list = new ArrayList<Object[]>();
        list.add(new Object[]{1,1001});
        list.add(new Object[]{1,1002});
        list.add(new Object[]{1,1003});

        jdbcTemplate.batchUpdate(sql,list);
    }

    //查询简单数据项(获取一个)
    @org.junit.Test
    public void testQuerySimple1(){
        String sql = "select count(*) from student";
        //得到返回值数量,第二个参数的意思是传出来的值为整数类型
        int count = jdbcTemplate.queryForObject(sql,Integer.class);
        System.out.println(count);

    }

    //查询复杂数据项(获取多个)
    @org.junit.Test
    public void testQuerySimple2(){
        String sql = "select name from student where sex=?";
        List<String> names = jdbcTemplate.queryForList(sql,String.class,"女");
        System.out.println(names);
    }

    //查询复杂数据项(获取一个)
    @org.junit.Test
    public void testQueryForMap1(){
        String sql = "select * from student where id = ?";
        Map<String,Object> stu = jdbcTemplate.queryForMap(sql,1);
        System.out.println(stu);
    }

    //查询复杂数据项(获取多个)
    @org.junit.Test
    public void testQueryForMap2(){
        String sql = "select * from student where id=? or id=?";
        List<Map<String, Object>> stu = jdbcTemplate.queryForList(sql,1,2);
        System.out.println(stu);
    }

    //查询复杂数据项(封装为对象)(获取一个)
    @org.junit.Test
    public void testQueryEntity1(){
        String sql = "select * from student where id = ?";
        //这里使用的是匿名内部类的方式进行映射
        Student stu = jdbcTemplate.queryForObject(sql, new StudentRowMapper(), 1);
        System.out.println(stu);
    }

    //查询复杂数据项(封装为对象)(获取多个)
    @org.junit.Test
    public void testQueryEntity2(){
        String sql = "select * from student";
        List<Student> stus = jdbcTemplate.query(sql, new StudentRowMapper());
        System.out.println(stus);
    }



    //尽量将其私有,可以隐藏实现细节
    //会看到使用匿名内部类的方式进行映射造成了代码的重用性过低,故此,可以直接自己声明一个
    private class StudentRowMapper implements RowMapper<Student>{

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
