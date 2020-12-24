package com.example.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.mybatisplus.entity.User;
import com.example.mybatisplus.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.nio.cs.US_ASCII;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 条件构造器
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class QueryWrapperTests {
    @Autowired
    private UserMapper userMapper;

    /**
     * ge、gt、le、lt、isNull、isNotNull
     *
     * UPDATE user SET deleted=1 WHERE deleted=0 AND name IS NULL AND 12 >= ? AND email IS NOT NULL
     */
    @Test
    public void testDelete(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("name").ge("12",12).isNotNull("email");
        int result = userMapper.delete(queryWrapper);
    }
    /**
     * eq、ne
     *
     * SELECT id,name,age,email,create_time,update_time,version,deleted FROM user WHERE deleted=0 AND name = ?
     */
    @Test
    public void testSelectOne(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","Tom");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }
    /**
     * between,notBetween
     *
     * SELECT COUNT( 1 ) FROM user WHERE deleted=0 AND age BETWEEN ? AND ?
     */
    @Test
    public void testSelectCount(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("age",20,30);
        Integer count = userMapper.selectCount(queryWrapper);
        System.out.println(count);
    }
    /**
     * allEq
     *
     * SELECT id,name,age,email,create_time,update_time,version,deleted FROM user WHERE deleted=0 AND name = ? AND id = ? AND age = ?
     */
    @Test
    public void testSelectList(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String,Object> map = new HashMap<>();
        map.put("id",2);
        map.put("name","Jack");
        map.put("age",20);
        queryWrapper.allEq(map);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
    /**
     * like,notLike,likeLeft,likeRight
     *
     * SELECT id,name,age,email,create_time,update_time,version,deleted FROM user WHERE deleted=0 AND name NOT LIKE ? AND email LIKE ?
     */
    @Test
    public void testSelectMaps(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.notLike("name","e").likeRight("email","t");
        List<Map<String,Object>> maps =userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }
    /**
     * in,notIn,inSql,notInSql,exists,notExists
     *
     *  SELECT id,name,age,email,create_time,update_time,version,deleted FROM user WHERE deleted=0 AND id IN (select id from user where id < 3 )
     */
    @Test
    public void testSelectObjs(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("id","select id from user where id < 3 ");
        List<Object> objects  = userMapper.selectObjs(queryWrapper);
        objects.forEach(System.out::println);
    }
    /**
     * or , and
     * 使用 updateWrapper
     * 注：不调用or则默认使用and连接
     *
     * UPDATE user SET age=?, email=?, update_time=? WHERE deleted=0 AND name LIKE ? OR age BETWEEN ? AND ?
     */
    @Test
    public void testUpdate1(){
        //修改值
        User user = new User();
        user.setAge(29);
        user.setEmail("Andy");
        //修改条件
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.like("name","h").or().between("age",20,30);
        int result = userMapper.update(user,updateWrapper);
        System.out.println(result);
    }
    /**
     * 嵌套or、嵌套and
     *
     * UPDATE user SET name=?, age=?, update_time=? WHERE deleted=0 AND name LIKE ? OR ( name = ? AND age <> ? )
     */
    @Test
    public void testUpdate2(){
        //修改值
        User user = new User();
        user.setAge(99);
        user.setName("Andy");
        //修改条件
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .like("name","h")
                .or(i -> i.eq("name", "李白").ne("age",20));
        int result = userMapper.update(user, updateWrapper);
        System.out.println(result);
    }
    /**
     * orderBy,OrderByDesc,orderByAsc
     *
     * SELECT id,name,age,email,create_time,update_time,version,deleted FROM user WHERE deleted=0 ORDER BY id DESC
     */
    @Test
    public void testSelectListOrderBy(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
    /**
     * last
     * 注：只能调用一次，多次调用以最后一次为准，有sql注入的风险，需谨慎使用
     *
     * SELECT id,name,age,email,create_time,update_time,version,deleted FROM user WHERE deleted=0 limit 1
     */
    @Test
    public void testSelectListLast(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("limit 1");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
    /**
     * 指定要查询的列
     *
     * SELECT id,name,age FROM user WHERE deleted=0
     */
    @Test
    public void testSelectListColumn(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name","age");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
    /**
     * set , setSql
     *
     * UPDATE user SET age=?, update_time=?, name=?,name=?, email = '123@qq.com' WHERE deleted=0 AND name LIKE ?
     */
    @Test
    public void testUpdateSet(){
        //修改值
        User user = new User();
        user.setAge(99);
        //修改条件
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .like("name","h")
                .set("name","h")
                .set("name","李老头") //除了可以查询还可以使用set设置修改的字段
                .setSql(" email = '123@qq.com'");//可以有子查询
        int result = userMapper.update(user,updateWrapper);
    }
}
