package com.petstore.service;

import com.petstore.dao.TypesDao;
import com.petstore.entity.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    @Autowired
    private TypesDao typeDao;

    /**
     * 通过id查询
     * @param typeId
     * @return
     */
    public Types get(Integer typeId) {
        return typeDao.selectById(typeId);
    }

    /**
     * 获取列表
     * @return
     */
    public List<Types> getList() {
        return typeDao.getList();
    }

    /**
     * 添加
     * @param type
     */
    public Integer add(Types type) {
        return typeDao.insert(type);
    }

    /**
     * 更新
     * @param type
     */
    public boolean update(Types type) {
        return typeDao.updateById(type)>0;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean delete(int id) {
        return typeDao.deleteById(id)>0;
    }
}
