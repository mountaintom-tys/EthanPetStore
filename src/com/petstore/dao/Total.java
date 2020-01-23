package com.petstore.dao;

import org.apache.ibatis.annotations.Select;

public interface Total {
    /**
     * 获取总数
     * @return
     */
    @Select("select count(*) from orders")
    long getTotal();

    /**
     * 通过类型获取总数
     * @param status
     * @return
     */
    @Select("select count(*) from orders where status=#{status}")
    long getTotalByStatus(byte status);
}
