package com.petstore.dao;

import com.petstore.entity.Items;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ItemsDao {

    int deleteById(Integer id);

    // 以上为mybatis generator自动生成接口, 具体实现在mapper.xml中

    // ------------------------------------------------------------

    // 以下方法使用mybatis注解实现
    /**
     * 订单项列表
     * @param orderId
     * @return
     */
    @Select("select * from items where order_id=#{orderId}")
    List<Items> getItemList(int orderId);

    /**
     * 插入订单项
     * @param item
     * @return
     */
    @Insert("insert into items (price,amount,order_id,cart_id,good_id) values(#{price},#{amount},#{orderId},#{cartId},#{goodId})")
    int insertItem(Items item);
}
