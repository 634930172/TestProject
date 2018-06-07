package com.john.testproject.entity;


import com.google.gson.annotations.SerializedName;
import com.john.testproject.network.SerializedEncryption;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/17 16:18
 * <p/>
 * Description: 登录需要提交的数据
 */
public class LoginSub {
    /** 手机号 */
    @SerializedName("loginName")
    private String id;
    /** 登录密码 */
    @SerializedEncryption(type = "MD5")
    @SerializedName("loginPwd")
    private String pwd;
    /** 设备指纹 */
    @SerializedName("blackBox")
    private String box;


    public LoginSub(String id, String pwd, String box) {
        this.id = id;
        this.pwd = pwd;
        this.box=box;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }
}
