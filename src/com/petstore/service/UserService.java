package com.petstore.service;

import com.petstore.dao.UsersDao;
import com.petstore.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional    // 注解此类所有方法加入spring事务, 具体设置默认
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

    /**
     * 通过phone获取
     * @param phone
     * @return
     */
    public Users getUserByPhone(String phone){
        return userDao.selectUserByPhone(phone);
    }
}
