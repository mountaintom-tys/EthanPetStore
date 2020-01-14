package com.petstore.dao;

import com.petstore.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsDao {
    int insert(Goods record);

    /**
     * 获取列表
     * @param i
     * @param rows
     * @return
     */
    @Select("select * from goods order by id desc limit #{begin},#{size}")
    public List<Goods> getList(@Param("begin") int i,@Param("size") int rows);
}
