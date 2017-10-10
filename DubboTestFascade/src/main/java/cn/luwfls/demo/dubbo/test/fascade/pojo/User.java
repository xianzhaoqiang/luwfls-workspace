package cn.luwfls.demo.dubbo.test.fascade.pojo;

import java.io.Serializable;

/**
 * @author luwenlong
 * @date 2017/9/29
 * @description 类描述
 */
public class User  implements Serializable{
    public enum SEX{
        MEN,WOMEN;
    }

    private String userName;
    private SEX sex;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public SEX getSex() {
        return sex;
    }

    public void setSex(SEX sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", sex=" + sex +
                '}';
    }
}


