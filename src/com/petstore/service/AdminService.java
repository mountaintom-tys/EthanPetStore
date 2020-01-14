package com.petstore.service;

import com.petstore.dao.AdminsDao;
import com.petstore.util.SafeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service  //注解为service层spring管理bean
@Transactional  //注解此类所有方法加入spring事务，具体设置默认
public class AdminService {
    @Autowired
    private AdminsDao adminDao;

    /*
    * 验证用户密码
    * @param username
    * @param password
    * @return
    * */
    public boolean checkUser(String username,String password){
        return adminDao.getByUsernameAndPassword(username, SafeUtil.encode(password))!=null;
    }
}
