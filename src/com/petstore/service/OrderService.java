package com.petstore.service;

import com.petstore.dao.ItemsDao;
import com.petstore.dao.OrdersDao;
import com.petstore.entity.Items;
import com.petstore.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional  //注解此类所有方法加入spring事务，具体设置默认
public class OrderService {
    @Autowired
    private OrdersDao orderDao;
    @Autowired
    private ItemsDao itemDao;
    @Autowired
    private GoodService goodService;
    @Autowired
    private UserService userService;
    @Autowired
    private TotalService totalService;
    /**
     * 获取列表
     * @param status
     * @return
     */
    public Map<String, Object> getMap(byte status,int page,int limit) {
        Map<String, Object> map = new HashMap<>();
        if (status == 1) {
            List<Orders> orderList=orderDao.getList(limit*(page-1),limit);
            for (Orders order : orderList) {
                order.setItemsList(this.getItemList(order.getId()));
                order.setUser(userService.get(order.getUserId()));
            }
            map = totalService.getMap(map, status, orderDao);
            map.put("data",orderList);
            return map;
        }
        return null;
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

    /**
     * 更新订单列表
     * @param id
     * @param type
     */
    public int orderUpdate(Integer id, Integer type) {
        Orders order = orderDao.selectById(id);
        if(type==Orders.STATUS_PAYED){
            order.setStatus(Orders.STATUS_PAYED);//订单付款
            return orderDao.updateByIdSelective(order);
        }else if(type==Orders.STATUS_SEND){
            order.setStatus(Orders.STATUS_SEND);//订单发货
            return orderDao.updateByIdSelective(order);
        }else if(type==Orders.STATUS_FINISH){
            order.setStatus(Orders.STATUS_FINISH);//订单完成
            return orderDao.updateByIdSelective(order);
        }else{
            return -1;
        }
    }

    public boolean deleteById(Integer id) {
        List<Items> itemList=itemDao.getItemList(id);
        for (Items item : itemList) {
            itemDao.deleteById(item.getId());
        }
        return orderDao.deleteById(id)>0;
    }
}
