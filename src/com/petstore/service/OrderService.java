package com.petstore.service;

import com.petstore.dao.ItemsDao;
import com.petstore.dao.OrdersDao;
import com.petstore.entity.Items;
import com.petstore.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private OrdersDao orderDao;
    @Autowired
    private ItemsDao itemDao;
    @Autowired
    private GoodService goodService;
    @Autowired UserService userService;
    /**
     * 获取列表
     * @param status
     * @return
     */
    public Map<String, Object> getList(byte status,int page,int limit) {
        Map<String, Object> map = new HashMap<>();
        if (status == 1) {
            List<Orders> orderList=orderDao.getList(limit*(page-1),limit);
            for (Orders order : orderList) {
                order.setItemsList(this.getItemList(order.getId()));
                order.setUser(userService.get(order.getUserId()));
            }
            map.put("code", 0);
            map.put("msg", "");
            map.put("count", this.getTotal(status));
            map.put("data",orderList);
            return map;
        }
        return null;
    }

    /**
     * 获取订单总数
     * @param status
     * @return
     */
    public long getTotal(int status){
        if(status==1){
            return orderDao.getTotal();
        }
        return orderDao.getTotalByStatus((byte)status);
    }

    /**
     * 获取订单项目列表
     * @param orderId
     * @return
     */
    private List<Items> getItemList(int orderId) {
        List<Items> itemList=itemDao.getItemList(orderId);
        for (Items item : itemList) {
            item.setGood(goodService.get(item.getGoodId()));
        }
        return itemList;
    }
}
