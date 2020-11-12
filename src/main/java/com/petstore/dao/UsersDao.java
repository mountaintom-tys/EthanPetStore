package com.petstore.dao;


import com.petstore.entity.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersDao {

    Users selectById(Integer userId);

    Users selectUserByPhone(String phone);

    int insertUser(Users user);

    int updateUser(Users user);

    // 以上为mybatis generator自动生成接口, 具体实现在mapper.xml中

    // ------------------------------------------------------------

    // 以下方法使用mybatis注解实现

}
