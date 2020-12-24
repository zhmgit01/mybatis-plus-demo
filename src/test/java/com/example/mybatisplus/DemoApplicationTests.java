package com.example.mybatisplus;

import com.example.mybatisplus.entity.User;
import com.example.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/*
@RunWith作用
    @RunWith 就是一个运行器
    @RunWith(JUnit4.class) 就是指用JUnit4来运行
    @RunWith(SpringJUnit4ClassRunner.class),让测试运行于Spring测试环境
    @RunWith(Suite.class) 的话就是一套测试集合，
    @ContextConfiguration Spring整合JUnit4测试时，使用注解引入多个配置文件
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList(){
        System.out.println("------------selectAll method test------------");
        /*
        UserMapper中的selectList()方法中的参数为MP内置的条件封装器Wrapper
        所以不填写就是无任何条件
         */
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

}
