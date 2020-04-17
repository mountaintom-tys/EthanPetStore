package com.petstore.dao;

import com.petstore.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsDao extends Total{
    int deleteById(Integer id);

    int insert(Goods record);

    Goods selectById(Integer id);

    int updateById(Goods good);
    // 以上为mybatis generator自动生成接口, 具体实现在mapper.xml中

    // ------------------------------------------------------------

    // 以下方法使用mybatis注解实现

    /**
     * 获取列表
     * @return
     */
    @Select("select * from goods where status=1 order by id desc limit #{begin},#{size}")
    List<Goods> getList(@Param("begin")int begin,@Param("size")int size);

    /**
     * 按类别获取列表
     * @return
     */
    @Select("select * from goods where type_id=#{type} and status=1 order by id desc limit #{begin},#{size}")
    List<Goods> getListByType(@Param("begin")int begin,@Param("size")int size,@Param("type")int type);
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
}
