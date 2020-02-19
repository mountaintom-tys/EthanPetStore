package com.petstore.service;

import com.petstore.dao.Total;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TotalService<T extends Total> {
    T dao;

    public Map<String, Object> getMap(Map<String, Object> map, byte type, T dao) {
        this.dao=dao;
        map.put("code", 0);
        map.put("msg", "");
        map.put("count",this.getTotal(type));
        return map;
    }
    /**
     * 获取产品总数
     * @param type
     * @return
     */
    public long getTotal(int type){
        if(type==0){
            return dao.getTotal();
        }
        return dao.getTotalByType((byte)type);
    }
}
