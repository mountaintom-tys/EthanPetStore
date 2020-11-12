package com.petstore.dao;

import com.petstore.entity.Admins;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminsDao {

    int updateById(Admins adminNew);

    // 以上为mybatis generator自动生成接口, 具体实现在mapper.xml中

    // ------------------------------------------------------------

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
