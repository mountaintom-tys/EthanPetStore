package com.petstore.dao;

import com.petstore.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrdersDao extends Total{

    int deleteById(Integer id);

    Orders selectById(Integer id);

    int updateByIdSelective(Orders order);

    int insertOrder(Orders order);

    // 以上为mybatis generator自动生成接口, 具体实现在mapper.xml中

    // ------------------------------------------------------------

    // 以下方法使用mybatis注解实现

    /**
     * 根据订单id获取订单对象
     * @param orderId
     * @return
     */
    @Select("select * from orders where id=#{orderId}")
    Orders selectOrder(int orderId);

    /**
     * 获取列表
     * @param begin
     * @param size
     * @return
     */
    @Select("select * from orders order by id desc limit #{begin},#{size}")
    List<Orders> getList(@Param("begin")int begin, @Param("size")int size);

    /**
     * 通过用户id获取订单列表
     * @param begin
     * @param size
     * @param userId
     * @return
     */
    @Select("select * from orders where user_id=#{userId} order by id desc limit #{begin},#{size}")
    List<Orders> getListByUserId(@Param("begin")int begin,@Param("size")int size,@Param("userId") int userId);

    /**
     * 按状态获取列表
     * @return
     */
    @Select("select * from orders where status=#{status} order by id desc limit #{begin},#{size}")
    List<Orders> getListByStatus(@Param("begin")int begin,@Param("size")int size,@Param("status")int status);

    /**
     * 按状态和用户id获取订单列表
     * @return
     */
    @Select("select * from orders where status=#{status} and user_id=#{userId} order by id desc limit #{begin},#{size}")
    List<Orders> getListByStatusByUserId(@Param("begin")int begin,@Param("size")int size,@Param("status")int status,@Param("userId") int userId);
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

    /**
     * 通过用户id获取总数
     * @return
     */
    @Select("select count(*) from orders where user_id=#{userId}")
    long getTotalByUserId(int userId);

    /**
     * 通过类型和用户id获取总数
     * @param type
     * @return
     */
    @Select("select count(*) from orders where status=#{type} and user_id=#{userId}")
    long getTotalByTypeAndUserId(@Param("type") byte type,@Param("userId") int userId);


}
