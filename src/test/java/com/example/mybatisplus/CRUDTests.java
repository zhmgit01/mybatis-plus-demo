package com.example.mybatisplus;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.entity.User;
import com.example.mybatisplus.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CRUDTests {

    @Autowired
    private UserMapper userMapper;

    /**
     * 测试插入数据
     */
    @Test
    public void testInsert(){
        User user = new User();
        user.setName("8889");
        user.setAge(22);
        user.setEmail("9@qq.com");

        int result = userMapper.insert(user);
        System.out.println(result);
        System.out.println(user);
    }

    /**
     * 测试更新数据
     */
    @Test
    public void testUpdate(){

        User user = new User();
        user.setId(1L);
        user.setAge(30);
        user.setEmail("98@qq.com");
        int result = userMapper.updateById(user);
        System.out.println(result);
    }

    /**
     * 测试乐观锁插件
     */
    @Test
    public void testOptimisiticLocker(){
        //查询
        User user = userMapper.selectById(1L);
        //修改数据
        user.setAge(38);
        user.setEmail("1@163.com");
        //执行更新
        userMapper.updateById(user);
    }

    /**
     * 根据id查询数据
     */
    @Test
    public void testSelectById(){
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    /**
     * 根据多个id批量查询数据
     */
    @Test
    public void testSelect(){
        List<User> userList = userMapper.selectBatchIds(Arrays.asList(1,2,3));
        userList.forEach(System.out::println);
    }

    /**
     * 通过map封装查询
     */
    @Test
    public void testSelectByMap(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","张三");
        map.put("age",38);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    /**
     * 测试selectPage分页
     */
    @Test
    public void testSelectPage(){
        Page<User> page = new Page<>(1,3);
        userMapper.selectPage(page,null);

        page.getRecords().forEach(System.out::println);
        System.out.println(page.getCurrent());
        System.out.println(page.getSize());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }

    /**
     * 测试selectMapsPage分页，结果集是Map
     */
    @Test
    public void testSelectMapsPage(){
        Page<User> page = new Page<>(1,5);
        IPage<Map<String ,Object>> mapIPage = userMapper.selectMapsPage(page,null);
        //此行必须使用mapIPage获取记录列表，否则会有数据转换错误
        mapIPage.getRecords().forEach(System.out::println);
        System.out.println(page.getCurrent());
        System.out.println(page.getSize());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }

    /**
     * 删除数据
     */
    @Test
    public void testDelete(){
        int result = userMapper.deleteById(8L);
        System.out.println(result);
    }
    /**
     * 批量删除数据
     */
    @Test
    public void testDeleteBatchIds(){
        int result = userMapper.deleteBatchIds(Arrays.asList(8,9,10));
        System.out.println(result);
    }
    /**
     * 简单的条件查询删除
     */
    @Test
    public void testDeleteByMap(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","小白");
        map.put("age",22);
        int result = userMapper.deleteByMap(map);
        System.out.println(result);
    }

    /**
     * 测试逻辑删除
     */
    @Test
    public void testLogicDelete(){
        int result = userMapper.deleteById(2L);
        System.out.println(result);
    }
    /**
     * 测试 逻辑删除后查询
     * 不包括被逻辑删除的记录
     */
    @Test
    public void testLogicDeleteSearch(){
        User user = new User();
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }
}
