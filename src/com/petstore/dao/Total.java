package com.petstore.dao;


public interface Total {
    /**
     * 获取总数
     * @return
     */
    long getTotal();

    /**
     * 通过用户id获取总数
     * @return
     */
    long getTotalByUserId(int userId);

    /**
     * 通过类型获取总数
     * @param type
     * @return
     */
    long getTotalByType(byte type);

    /**
     * 通过类型和用户id获取总数
     * @param type
     * @return
     */
    long getTotalByTypeAndUserId(byte type,int userId);

    /**
     * 通过商品名模糊查询
     * @param fuzzyGoodName
     * @return
     */
    long getTotalByFuzzyGoodName(String fuzzyGoodName);
}
