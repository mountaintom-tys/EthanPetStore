package com.petstore.dao;

import com.petstore.entity.Carts;
import com.petstore.entity.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface GoodsDao extends Total{
    int deleteById(Integer id);

    int insert(Goods record);

    Goods selectById(Integer id);

    int updateById(Goods good);
    // 以上为mybatis generator自动生成接口, 具体实现在mapper.xml中

    // ------------------------------------------------------------

    // 以下方法使用mybatis注解实现

    /**
     * 获取商品列表
     * @return
     */
    @Select("select * from goods where status=1 order by id desc limit #{begin},#{size}")
    List<Goods> getList(@Param("begin")int begin,@Param("size")int size);

    /**
     * 按类别获取商品列表
     * @return
     */
    @Select("select * from goods where type_id=#{type} and status=1 order by id desc limit #{begin},#{size}")
    List<Goods> getListByType(@Param("begin")int begin,@Param("size")int size,@Param("type")int type);

    /**
     * 根据商品名称模糊查询
     * @param fuzzyGoodName
     * @param begin
     * @param size
     * @return
     */
    @Select("select * from goods where name like '%${fuzzyGoodName}%' limit #{begin},#{size}")
    List<Goods> getFuzzyList(@Param("fuzzyGoodName")String fuzzyGoodName,@Param("begin") int begin,@Param("size") Integer size);
    /**
     * 获取总数
     * @return
     */
    @Select("select count(*) from goods where status=1")
    long getTotal();

    /**
     * 通过类型获取总数
     * @param type
     * @return
     */
    @Select("select count(*) from goods where type_id=#{type}")
    long getTotalByType(byte type);

    /**
     * 通过商品名模糊查询
     * @param fuzzyGoodName
     * @return
     */
    @Select("select count(*) from goods where name like '%${fuzzyGoodName}%'")
    long getTotalByFuzzyGoodName(@Param("fuzzyGoodName") String fuzzyGoodName);

    /**
     * 通过用户id和商品id查询collections表记录
     * @param userId
     * @param goodId
     * @return
     */
    @Select("select count(*) from collections where user_id=#{userId} and good_id=#{goodId}")
    int selectGoodCollection(@Param("userId") int userId,@Param("goodId") int goodId);

    /**
     * 通过商品id获取该商品被收藏的总次数
     * @param goodId
     * @return
     */
    @Select("select count(*) from collections where good_id=#{goodId}")
    int getGoodCollectedCount(int goodId);

    /**
     * 新增商品收藏
     * @param userId
     * @param goodId
     */
    @Select("insert into collections(user_id,good_id) values(#{userId},#{goodId})")
    void insertGoodCollection(@Param("userId") int userId,@Param("goodId") int goodId);

    /**
     * 删除商品收藏
     * @param userId
     * @param goodId
     */
    @Select("delete from collections where user_id=#{userId} and good_id=#{goodId}")
    void deleteGoodCollection(@Param("userId")int userId,@Param("goodId") int goodId);

    /**
     * 获取被收藏最多的商品id和数量
     * @return
     */
    @Select("SELECT good_id,count(*) count  FROM `collections` GROUP BY good_id HAVING count=(select max(count) from(SELECT good_id,count(*) count  FROM `collections` GROUP BY good_id) r) limit 0,#{size}")
    List<Map<String,Integer>> selectMostCollectedGoodIdAndCount(int size);

    /**
     * 获取所有被收藏过的商品
     * @param size
     * @return
     */
    @Select("SELECT good_id,count(*) count  FROM `collections` GROUP BY good_id order by count desc limit 0,#{size}")
    List<Map<String,Integer>> selectBeenCollectedGoodIdAndCount(int size);

    /**
     * 获取收藏商品列表
     *
     * @param userId
     * @param begin
     * @param size
     * @return
     */
    @Select("select g.* from goods g inner join collections c on g.id=c.good_id where g.status=1 and c.user_id=#{userId} order by g.id desc limit #{begin},#{size}")
    List<Goods> getCollectedGoodList(@Param("userId")Integer userId, @Param("begin")int begin, @Param("size")int size);

    /**
     * 按商品类型获取收藏商品列表
     * @param userId
     * @param begin
     * @param size
     * @param type
     * @return
     */
    @Select("select g.* from goods g inner join collections c on g.id=c.good_id where g.status=1 and c.user_id=#{userId} and g.type=#{type} order by g.id desc limit #{begin},#{size}")
    List<Goods> getCollectedGoodListByType(@Param("userId")Integer userId, @Param("begin")int begin, @Param("size")int size,@Param("type") int type);

    /**
     * 加入购物车
     * @param userId
     * @param goodId
     * @param amount
     * @return
     */
    @Insert("insert into carts (amount,good_id,user_id) values (#{amount},#{goodId},#{userId})")
    int insertCart(@Param("userId") Integer userId, @Param("goodId") Integer goodId, @Param("amount") Integer amount);

    /**
     * 根据用户id查询购物车
     * @param userId
     * @return
     */
    @Select("select * from carts where user_id=#{userId}")
    List<Carts> selectShopCartByUserId(Integer userId);

    /**
     * 根据用户id查询购物车
     * @param userId
     * @return
     */
    @Select("select * from carts where user_id=#{userId} and good_id=#{goodId}")
    Carts selectShopCart(@Param("userId") Integer userId,@Param("goodId") Integer goodId);

    /**
     * 修改购物车商品数量
     * @param userId
     * @param goodId
     * @param amount
     * @return
     */
    @Update("update carts set amount=#{amount} where user_id=#{userId} and good_id=#{goodId}")
    int updateCart(@Param("userId") Integer userId,@Param("goodId") Integer goodId,@Param("amount") Integer amount);

    /**
     * 根据用户id和商品id删除购物车
     * @param userId
     * @param goodId
     * @return
     */
    @Delete("delete from carts where user_id=#{userId} and good_id=#{goodId}")
    int deleteFromCart(@Param("userId") Integer userId,@Param("goodId") Integer goodId);

    /**
     * 根据用户id删除购物车
     * @param userId
     * @return
     */
    @Delete("delete from carts where user_id=#{userId}")
    int deleteFromCartByUserId(Integer userId);

}
