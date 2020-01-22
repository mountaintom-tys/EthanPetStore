package com.petstore.service;

import com.petstore.dao.AdminsDao;
import com.petstore.entity.Admins;
import com.petstore.util.SafeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service  //注解为service层spring管理bean
@Transactional  //注解此类所有方法加入spring事务，具体设置默认
public class AdminService {
    @Autowired
    private AdminsDao adminDao;

    /**
    * 验证用户密码
    * @param username
    * @param password
    * @return
    */
    public boolean checkUser(String username,String password){
        return adminDao.getByUsernameAndPassword(username, SafeUtil.encode(password))!=null;
    }

    /**
     * 通过用户名获取
     * @param username
     * @return
     */
    public Admins getByUserName(String username) {
        return adminDao.getByUserName(username);
    }

    /**
     * 更新
     * @param adminNew
     */
    public boolean update(Admins adminNew) {
        return adminDao.updateById(adminNew)>0;
    }

    /**
     * 用户是否存在
     * @param adminNew
     * @return
     */
    public boolean isExist(Admins adminNew) {
        Admins admindb=adminDao.getByUserName(adminNew.getUsername());
        return !admindb.getId().equals(adminNew.getId());
    }
}
