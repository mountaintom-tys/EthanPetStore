package com.petstore.entity;

import org.springframework.util.StringUtils;

/**
 * 用户
 */
public class Users {
    private Integer id;

    private String username;

    private String password;
    //收货人姓名，通常是当前用户的真实姓名
    private String name;

    private String phone;

    private String address;

    private String securityQuestion;//密保

    private String securityAnswer;

    private String passwordNew;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getPasswordNew() {
        return passwordNew;
    }

    public void setPasswordNew(String passwordNew) {
        this.passwordNew = passwordNew;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;//地址相等
        }
        if(obj == null){
            return false;//非空性：对于任意非空引用x，x.equals(null)应该返回false。
        }
        if(obj instanceof Users){
            Users other = (Users) obj;
            //需要比较的字段相等，则这两个对象相等
            if(equalsStr(this.username, other.username)
                    && equalsStr(this.password, other.password)&&equalsStr(this.passwordNew,other.passwordNew)
                    &&this.securityQuestion.equals(other.securityQuestion)&&this.securityAnswer.equals(other.securityAnswer)
                    &&this.phone.equals(other.phone)&&equalsStr(this.name,other.name)&&equalsStr(this.address,other.address)){
                return true;
            }
        }
        return false;
    }
    private boolean equalsStr(String str1, String str2){
        if(StringUtils.isEmpty(str1) && StringUtils.isEmpty(str2)){
            return true;
        }
        if(!StringUtils.isEmpty(str1) && str1.equals(str2)){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result=17;
        result=31*result+(username==null?0:username.hashCode());
        result=31*result+(password==null?0:password.hashCode());
        result=31*result+(passwordNew==null?0:passwordNew.hashCode());
        result=31*result+(securityQuestion==null?0:securityQuestion.hashCode());
        result=31*result+(securityAnswer==null?0:securityAnswer.hashCode());
        result=31*result+(phone==null?0:phone.hashCode());
        result=31*result+(name==null?0:name.hashCode());
        result=31*result+(address==null?0:address.hashCode());
        return result;
    }
}