package com.petstore.dao;

import com.petstore.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsDao {
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
    @Select("select * from goods order by id desc limit #{begin},#{size}")
    List<Goods> getList(@Param("begin")int begin,@Param("size")int size);


    /**
     * 获取总数
     * @return
     */
    @Select("select count(*) from goods")
    long getTotal();

    /**
     * 通过类型获取总数
     * @param type
     * @return
     */
    @Select("select count(*) from goods where type_id=#{type}")
    long getTotalByType(byte type);
}
