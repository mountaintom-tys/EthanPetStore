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
     * 获取商品列表
     * @param type
     * @param page
     * @param limit
     * @return
     */
    public Map<String, Object> getMap(byte type,int page,int limit) {
        Map<String, Object> map = new HashMap<>();
        List<Goods> goodList;
        if (type == 0) {//返回所有类型商品集合
            goodList=goodDao.getList(limit*(page-1),limit);
            map = totalService.getMap(map, type, goodDao);//获取数据库中当前类型商品总数量
            map.put("data",packToList(goodList));
            return map;
        }else{//返回指定类型商品集合
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
        Goods good=goodDao.selectById(id);
        if(Objects.nonNull(good)){
            good.setType(typeService.get(good.getTypeId()));
        }
        return good;
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

    /**
     * 判断用户是否收藏了该商品
     * @param userId
     * @param goodId
     * @return
     */
    public boolean getGoodCollectedStatus(Integer userId, int goodId) {
        return goodDao.selectGoodCollection(userId,goodId)>0;
    }

    /**
     * 获取该商品被收藏的总次数
     * @param goodId
     * @return
     */
    public int getGoodCollectedCount(int goodId) {
        return goodDao.getGoodCollectedCount(goodId);
    }

    /**
     * 新增商品收藏
     * @param userId
     * @param goodId
     */
    public void addGoodCollection(Integer userId, int goodId) {
        goodDao.insertGoodCollection(userId,goodId);
    }

    /**
     * 删除商品收藏
     * @param userId
     * @param goodId
     */
    public void deleteGoodCollection(Integer userId, int goodId) {
        goodDao.deleteGoodCollection(userId,goodId);
    }

    /**
     * 获取被收藏最多的商品id和数量
     * @return
     */
    public List<Map<String,Integer>> getMostCollectedGoodIdAndCount(){
        return goodDao.selectMostCollectedGoodIdAndCount();
    }

    /**
     * 获取收藏商品列表
     *
     * @param userId
     * @param type
     * @param page
     * @param limit
     * @return
     */
    public Map<String, Object> getCollectedGoodMap(Integer userId, byte type, int page, int limit) {
        Map<String, Object> map = new HashMap<>();
        List<Goods> goodList;
        if (type == 0) {//返回当前用户收藏的所有类型商品集合
            goodList=goodDao.getCollectedGoodList(userId,limit*(page-1),limit);
            map = totalService.getMap(map, type, goodDao);//获取数据库中当前类型商品总数量
            map.put("data",packToList(goodList));
            return map;
        }else{//返回回当前用户收藏的指定类型商品集合
            goodList=goodDao.getCollectedGoodListByType(userId,limit*(page-1),limit,type);
            map = totalService.getMap(map, type, goodDao);
            map.put("data",packToList(goodList));
            return map;
        }
    }
}
