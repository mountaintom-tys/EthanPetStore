package com.petstore.dao;

import com.petstore.entity.Types;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TypesDao extends Total{

    Types selectById(Integer typeId);

    Integer insert(Types type);

    int updateById(Types type);

    int deleteById(Integer id);

    // 以上为mybatis generator自动生成接口, 具体实现在mapper.xml中

    // ------------------------------------------------------------

    // 以下方法使用mybatis注解实现

    /**
     * 获取分页列表
     * @return
     */
    @Select("select * from types order by id desc limit #{begin},#{size}")
    List<Types> getList(@Param("begin")int begin,@Param("size")int size);

    /**
     * 获取所有列表
     * @return
     */
    @Select("select * from types order by id desc")
    List<Types> getListTotal();
    /**
     * 获取总数
     * @return
     */
    @Select("select count(*) from types")
    long getTotal();

    /**
     * 通过类型获取总数
     * @param type
     * @return
     */
    @Select("select count(*) from types where status=#{status}")
    long getTotalByType(byte type);

}
