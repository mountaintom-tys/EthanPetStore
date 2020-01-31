package com.petstore.service;

import com.petstore.dao.TypesDao;
import com.petstore.entity.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional    // 注解此类所有方法加入spring事务, 具体设置默认
public class TypeService {
    @Autowired
    private TypesDao typeDao;
    @Autowired
    private TotalService totalService;

    /**
     * 通过id查询
     *
     * @param typeId
     * @return
     */
    public Types get(Integer typeId) {
        return typeDao.selectById(typeId);
    }

    /**
     * 获取分页列表
     *
     * @return
     */
    public Map<String, Object> getMap(byte type, int page, int limit) {
        Map<String, Object> map = new HashMap<>();
        if (type == 1) {
            List<Types> typeList = typeDao.getList(limit * (page - 1), limit);
            map = totalService.getMap(map, type, typeDao);
            map.put("data", typeList);
            return map;
        }
        return null;
    }

    /**
     * 获取所有列表
     * @return
     */
    public List<Types> getList() {
        return typeDao.getListTotal();
    }


    /**
     * 添加
     *
     * @param type
     */
    public Integer add(Types type) {
        return typeDao.insert(type);
    }

    /**
     * 更新
     *
     * @param type
     */
    public boolean update(Types type) {
        return typeDao.updateById(type) > 0;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public boolean delete(int id) {
        return typeDao.deleteById(id) > 0;
    }
}
