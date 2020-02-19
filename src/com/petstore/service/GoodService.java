package com.petstore.service;

import com.petstore.dao.GoodsDao;
import com.petstore.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional    // 注解此类所有方法加入spring事务, 具体设置默认
public class GoodService {
    @Autowired
    private GoodsDao goodDao;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TotalService totalService;

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
    public Map<String, Object> getMap(byte type,int page,int limit) {
        Map<String, Object> map = new HashMap<>();
        List<Goods> goodList;
        if (type == 0) {
            goodList=goodDao.getList(limit*(page-1),limit);
            map = totalService.getMap(map, type, goodDao);
            map.put("data",packToList(goodList));
            return map;
        }else{
            goodList=goodDao.getListByType(limit*(page-1),limit,type);
            map = totalService.getMap(map, type, goodDao);
            map.put("data",packToList(goodList));
            return map;
        }
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
