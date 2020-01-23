package com.petstore.service;

import com.petstore.dao.GoodsDao;
import com.petstore.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class GoodService {
    @Autowired
    private GoodsDao goodDao;
    @Autowired
    private TypeService typeService;

    /**
     * 添加
     *
     * @param good
     * @return
     */
    public Integer add(Goods good) {
        return goodDao.insert(good);
    }

    /**
     * 获取列表
     *
     * @param type
     */
    public Map<String, Object> getList(byte type,int page,int limit) {
        Map<String, Object> map = new HashMap<>();
        if (type == 1) {
            List<Goods> goodList=goodDao.getList(limit*(page-1),limit);
            map.put("code", 0);
            map.put("msg", "");
            map.put("count", this.getTotal(type));
            map.put("data",packToList(goodList));
            return map;
        }
        return null;
    }

    /**
     * 获取产品总数
     * @param type
     * @return
     */
    public long getTotal(int type){
        if(type==1){
            return goodDao.getTotal();
        }
        return goodDao.getTotalByType((byte)type);
    }
    /**
     * 封装商品信息
     * @param list
     * @return
     */
    private List<Goods> packToList(List<Goods> list) {
        for (Goods good : list) {
            good.setType(typeService.get(good.getTypeId()));
        }
        return list;
    }

    /**
     * 通过id获取商品
     * @param id
     * @return
     */
    public Goods get(int id) {
        Goods goods=goodDao.selectById(id);
        if(Objects.nonNull(goods)){
            goods.setType(typeService.get(goods.getTypeId()));
        }
        return goods;
    }

    /**
     * 修改
     * @param good
     */
    public boolean update(Goods good) {
        return goodDao.updateById(good)>0;
    }

    /**
     * 删除商品
     * @param id
     * @return
     */
    public boolean delete(int id) {
        return goodDao.deleteById(id)>0;
    }
}
