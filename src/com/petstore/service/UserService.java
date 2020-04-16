package com.petstore.service;

import com.petstore.dao.UsersDao;
import com.petstore.entity.Users;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional    // 注解此类所有方法加入spring事务, 具体设置默认
public class UserService {
    static Logger logger = Logger.getLogger(UserService.class);
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

    /**
     * 新增用户
     * @param user
     * @return
     */
    public int addUser(Users user){
        try {
            return userDao.insertUser(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return -1;
        }
    }

    public int updateUser() {
        return 0;
    }
}
