package com.petstore.service;

import com.petstore.dao.GoodsDao;
import com.petstore.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GoodService {
    @Autowired
    private GoodsDao goodDao;
    /**
     * 添加
     * @param good
     * @return
     */
    public Integer add(Goods good){
        return goodDao.insert(good);
    }

    /**
     * 获取列表
     * @param type
     * @param page
     * @param rows
     */
    public Map<String, Object> getList(byte type, int page, int rows) {
        Map<String, Object> map = new HashMap<>();
        if(type==1){
           map.put("code",0);
           map.put("msg","");
           map.put("count",1000);
           map.put("data",goodDao.getList(rows*(page-1),rows));
           return map;
        }
        return null;
    }
}
