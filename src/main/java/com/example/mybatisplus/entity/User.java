package com.example.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
public class User {

    /**
    自增策略，数据库中将id字段设置为 主键自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private int age;
    private String email;

    /**
    新增数据时插入
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 新增或修改数据时，插入或更新
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 支持的数据类型只有 int Integer long Long Date Timestamp LocalDateTime
     * 整数类型下 newVersion = oldVersion + 1
     * newVersion 会回写到 entity中
     * 仅支持 updateById(id)  与 update(entity, wrapper)方法
     * 在update(entity, wrapper)方法下，wrapper不能复用！
     */
    @Version
    private Integer version;
    /**
     * 逻辑删除字段
     */
    @TableLogic
    private Integer deleted;
}
