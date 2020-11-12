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
        if (status == 0) {
            List<Orders> orderList=orderDao.getList(limit*(page-1), limit);
            setDataForOrder(orderList);
            map = totalService.getMap(map, status, orderDao);
            map.put("data",orderList);
            return map;
        }
        else{
            List<Orders> orderList=orderDao.getListByStatus(limit*(page-1),limit,status);
            setDataForOrder(orderList);
            map = totalService.getMap(map, status, orderDao);
            map.put("data",orderList);
            return map;
        }
    }

    /**
     * 根据订单id获取订单对象
     * @param orderId
     * @return
     */
    public Orders getOrder(int orderId){
        Orders order=orderDao.selectOrder(orderId);
        if(order!=null){
            order.setItemsList(this.getItemList(order.getId()));
            order.setUser(userService.get(order.getUserId()));
        }
        return order;
    }

    /**
     * 根据用户id获取订单列表
     * @param status
     * @return
     */
    public Map<String, Object> getMap(byte status,int page,int limit,int userId) {
        Map<String, Object> map = new HashMap<>();
        if (status == 0) {
            List<Orders> orderList=orderDao.getListByUserId(limit*(page-1),limit,userId);
            setDataForOrder(orderList);
            map = totalService.getMapByUserId(map, status, orderDao,userId);
            map.put("data",orderList);
            return map;
        }
        else{
            List<Orders> orderList=orderDao.getListByStatusByUserId(limit*(page-1),limit,status,userId);
            setDataForOrder(orderList);
            map = totalService.getMapByUserId(map, status, orderDao,userId);
            map.put("data",orderList);
            return map;
        }
    }

    /**
     * 为定订单列表中的每一条订单设置订单项列表、用户实体
     * @param orderList
     */
    public void setDataForOrder(List<Orders> orderList){
        for (Orders order : orderList) {
            order.setItemsList(this.getItemList(order.getId()));
            order.setUser(userService.get(order.getUserId()));
        }
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

    /**
     * 更新订单列表付款方式
     * @param id
     * @param type
     */
    public int orderPayTypeUpdate(Integer id, Integer type) {
        Orders order = orderDao.selectById(id);
        if(type==Orders.PAYTYPE_WECHAT){
            order.setPaytype(Orders.PAYTYPE_WECHAT);//微信方式付款
            return orderDao.updateByIdSelective(order);
        }else if(type==Orders.PAYTYPE_ALIPAY){
            order.setPaytype(Orders.PAYTYPE_ALIPAY);//支付宝方式付款
            return orderDao.updateByIdSelective(order);
        }else if(type==Orders.PAYTYPE_OFFLINE){
            order.setPaytype(Orders.PAYTYPE_OFFLINE);//订单完成
            return orderDao.updateByIdSelective(order);
        }else{
            return -1;
        }
    }

    @Transactional
    public boolean deleteById(Integer id) {
        List<Items> itemList=itemDao.getItemList(id);
        for (Items item : itemList) {
            itemDao.deleteById(item.getId());
        }
        return orderDao.deleteById(id)>0;
    }

    @Transactional
    public void addOrder(Orders order) {
        orderDao.insertOrder(order);
        for (Items item : order.getItemsList()) {
            item.setOrderId(order.getId());
            itemDao.insertItem(item);
        }
    }
}
