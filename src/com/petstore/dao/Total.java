package com.petstore.dao;


public interface Total {
    /**
     * 获取总数
     * @return
     */
    long getTotal();

    /**
     * 通过类型获取总数
     * @param type
     * @return
     */
    long getTotalByType(byte type);
}
