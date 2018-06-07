package com.john.testproject.greendao.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/2/1 16:09
 * Description:Customer数据库
 */
@Entity
public class Customer {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private int age;

    @Transient
    private int tempUsageCount; // not persisted
    @Generated(hash = 463016582)
    public Customer(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    @Generated(hash = 60841032)
    public Customer() {
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
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }

}
