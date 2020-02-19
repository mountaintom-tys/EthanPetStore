package com.petstore.dao;

import com.petstore.entity.Orders;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrdersDao extends Total{

    int deleteById(Integer id);

    Orders selectById(Integer id);

    int updateByIdSelective(Orders order);

    // 以上为mybatis generator自动生成接口, 具体实现在mapper.xml中

    // ------------------------------------------------------------

    // 以下方法使用mybatis注解实现

    /**
     * 获取列表
     * @param begin
     * @param size
     * @return
     */
    @Select("select * from orders order by id desc limit #{begin},#{size}")
    List<Orders> getList(@Param("begin")int begin,@Param("size")int size);

    /**
     * 按状态获取列表
     * @return
     */
    @Select("select * from orders where status=#{status} order by id desc limit #{begin},#{size}")
    List<Orders> getListByStatus(@Param("begin")int begin,@Param("size")int size,@Param("status")int status);

    /**
     * 获取总数
     * @return
     */
    @Select("select count(*) from orders")
    long getTotal();

    /**
     * 通过类型获取总数
     * @param type
     * @return
     */
    @Select("select count(*) from orders where status=#{type}")
    long getTotalByType(byte type);


}
