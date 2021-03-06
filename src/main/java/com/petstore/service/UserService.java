package com.petstore.service;

import com.petstore.dao.UsersDao;
import com.petstore.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional    // 注解此类所有方法加入spring事务, 具体设置默认
@Slf4j
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

    /**
     * 新增用户
     * @param user
     * @return
     */
    public int addUser(Users user){
        try {
            return userDao.insertUser(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
    }

    public int updateUser(Users user) {
        try {
            return userDao.updateUser(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
    }

    /**
     * 用户是否存在
     * @param userNew
     * @return
     */
    public boolean isExist(Users userNew) {
        Users userdb=userDao.selectUserByPhone(userNew.getPhone());
        if(userdb!=null){
            return !userdb.getId().equals(userNew.getId());
        }else{
            return false;
        }
    }
}
