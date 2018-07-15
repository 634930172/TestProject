package com.john.testproject.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/2/1 14:25
 * Description:用户数据库
 */
@Entity
public class User {
    @Id
    private Long id;
    private String name;
    @Transient
    private int tempUsageCount; // not persisted
    @Generated(hash = 873297011)
    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
