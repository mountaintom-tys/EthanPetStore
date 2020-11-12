package com.petstore.entity;

import org.springframework.util.StringUtils;

/**
 * 管理员
 */
public class Admins {
    private Integer id;

    private String username;

    private String password;
    
    private String passwordNew;

    private String securityQuestion;

    private String securityAnswer;

    public String getPasswordNew() {
		return passwordNew;
	}

	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}

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

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;//地址相等
        }
        if(obj == null){
            return false;//非空性：对于任意非空引用x，x.equals(null)应该返回false。
        }
        if(obj instanceof Admins){
            Admins other = (Admins) obj;
            //需要比较的字段相等，则这两个对象相等
            if(equalsStr(this.username, other.username)
                    && equalsStr(this.password, other.password)&&equalsStr(this.passwordNew,other.passwordNew)
                    &&this.securityQuestion.equals(other.securityQuestion)&&this.securityAnswer.equals(other.securityAnswer)){
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
        return result;
    }
}