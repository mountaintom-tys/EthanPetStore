package com.petstore.dao;

import com.petstore.entity.Admins;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AdminsDao {

    //以下方法使用mybatis注解实现
    /*
    * 通过用户名和密码查找
    * @param username
    * @param password
    * @return 无记录返回null
    * */
    @Select("select * from admins where username=#{username} and password=#{password}")
    public Admins getByUsernameAndPassword(@Param("username")String username,@Param("password")String password);

    /**
     * 通过用户名查找
     * @param username
     * @return
     */
    @Select("select * from admins where username=#{username}")
    Admins getByUserName(String username);
}
