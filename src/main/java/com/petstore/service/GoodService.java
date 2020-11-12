package com.petstore.service;

import com.petstore.dao.GoodsDao;
import com.petstore.entity.Carts;
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
     *
     * @param type
     * @param page
     * @param limit
     * @return
     */
    public Map<String, Object> getMap(byte type, int page, int limit) {
        Map<String, Object> map = new HashMap<>();
        List<Goods> goodList;
        if (type == 0) {//返回所有类型商品集合
            goodList = goodDao.getList(limit * (page - 1), limit);
            map = totalService.getMap(map, type, goodDao);//获取数据库中当前类型商品总数量
            map.put("data", packToList(goodList));
            return map;
        } else {//返回指定类型商品集合
            goodList = goodDao.getListByType(limit * (page - 1), limit, type);
            map = totalService.getMap(map, type, goodDao);
            map.put("data", packToList(goodList));
            return map;
        }
    }

    /**
     * 根据商品名模糊查询
     * @param fuzzyGoodName
     * @param type
     * @param page
     * @param limit
     * @return
     */
    public Map<String, Object> getFuzzyGoodMap(String fuzzyGoodName, byte type, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        List<Goods> goodList;
        if (type == 0) {//返回所有类型商品集合
            goodList = goodDao.getFuzzyList(fuzzyGoodName,limit * (page - 1), limit);
            map = totalService.getMapByFuzzyGoodName(fuzzyGoodName,map, type, goodDao);//获取数据库中当前类型商品总数量
            map.put("data", packToList(goodList));
            return map;
        }
//        else {//返回指定类型商品集合
//            goodList = goodDao.FuzzyByType(fuzzyGoodName,limit * (page - 1), limit, type);
//            map = totalService.getMapByFuzzyGoodName(fuzzyGoodName,map, type, goodDao);
//            map.put("data", packToList(goodList));
//            return map;
//        }
        return null;
    }

    /**
     * 封装商品信息
     *
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
     *
     * @param id
     * @return
     */
    public Goods get(int id) {
        Goods good = goodDao.selectById(id);
        if (Objects.nonNull(good)) {
            good.setType(typeService.get(good.getTypeId()));
        }
        return good;
    }

    /**
     * 修改
     *
     * @param good
     */
    public boolean update(Goods good) {
        return goodDao.updateById(good) > 0;
    }

    /**
     * 删除商品
     *
     * @param id
     * @return
     */
    public boolean delete(int id) {
        return goodDao.deleteById(id) > 0;
    }

    /**
     * 判断用户是否收藏了该商品
     *
     * @param userId
     * @param goodId
     * @return
     */
    public boolean getGoodCollectedStatus(Integer userId, int goodId) {
        return goodDao.selectGoodCollection(userId, goodId) > 0;
    }

    /**
     * 获取该商品被收藏的总次数
     *
     * @param goodId
     * @return
     */
    public int getGoodCollectedCount(int goodId) {
        return goodDao.getGoodCollectedCount(goodId);
    }

    /**
     * 新增商品收藏
     *
     * @param userId
     * @param goodId
     */
    public void addGoodCollection(Integer userId, int goodId) {
        goodDao.insertGoodCollection(userId, goodId);
    }

    /**
     * 删除商品收藏
     *
     * @param userId
     * @param goodId
     */
    public void deleteGoodCollection(Integer userId, int goodId) {
        goodDao.deleteGoodCollection(userId, goodId);
    }

    /**
     * 获取被收藏最多的商品id和数量
     *
     * @return
     */
    public List<Map<String, Integer>> getMostCollectedGoodIdAndCount(int limit) {
        if(limit>=0){
            return goodDao.selectMostCollectedGoodIdAndCount(limit);
        }else{
            limit=limit*(-1);
            return goodDao.selectBeenCollectedGoodIdAndCount(limit);
        }
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
            goodList = goodDao.getCollectedGoodList(userId, limit * (page - 1), limit);
            map = totalService.getMap(map, type, goodDao);//获取数据库中当前类型商品总数量
            map.put("data", packToList(goodList));
            return map;
        } else {//返回回当前用户收藏的指定类型商品集合
            goodList = goodDao.getCollectedGoodListByType(userId, limit * (page - 1), limit, type);
            map = totalService.getMap(map, type, goodDao);
            map.put("data", packToList(goodList));
            return map;
        }
    }

    /**
     * 加入购物车
     *
     * @param userId
     * @param goodId
     * @param amount
     * @return
     */
    public int addToCart(Integer userId, Integer goodId, Integer amount) {
        Carts cart=goodDao.selectShopCart(userId, goodId);
        if(cart!=null){
            return changeCartAmount(userId,goodId,cart.getAmount()+amount);
        }else {
            return goodDao.insertCart(userId, goodId, amount);
        }
    }

    /**
     * 根据用户id查询购物车
     *
     * @param userId
     * @return
     */
    public List<Carts> getShopCart(Integer userId) {
        List<Carts> cartsList = goodDao.selectShopCartByUserId(userId);
        for (Carts cart : cartsList) {
            Goods good=goodDao.selectById(cart.getGoodId());
            good.setType(typeService.get(good.getTypeId()));
            cart.setGood(good);
        }
        return cartsList;
    }

    /**
     * 根据用户id和商品id查询购物车
     *
     * @param userId
     * @return
     */
    public Carts getShopCart(Integer userId, Integer goodId) {
        Carts cart = goodDao.selectShopCart(userId, goodId);
        Goods good=goodDao.selectById(cart.getGoodId());
        good.setType(typeService.get(good.getTypeId()));
        cart.setGood(good);
        return cart;
    }

    /**
     * 修改购物车商品数量
     *
     * @param userId
     * @param goodId
     * @param amount
     * @return
     */
    public int changeCartAmount(Integer userId, Integer goodId, Integer amount) {
        return goodDao.updateCart(userId, goodId, amount);
    }

    /**
     * 根据用户id和商品id删除购物车
     *
     * @param userId
     * @param goodId
     */
    public int deleteFromCart(Integer userId, Integer goodId) {
        return goodDao.deleteFromCart(userId, goodId);
    }

    /**
     * 根据用户id删除购物车
     *
     * @param userId
     */
    public int deleteFromCart(Integer userId) {
        return goodDao.deleteFromCartByUserId(userId);
    }

}
