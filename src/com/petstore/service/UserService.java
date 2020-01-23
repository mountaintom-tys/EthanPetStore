package com.petstore.service;

import com.petstore.dao.UsersDao;
import com.petstore.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UsersDao userDao;

    /**
     * 通过id获取
     * @param userId
     * @return
     */
    public Users get(Integer userId) {
        return userDao.selectById(userId);
    }
}
