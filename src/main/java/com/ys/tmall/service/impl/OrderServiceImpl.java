package com.ys.tmall.service.impl;

import com.ys.tmall.bean.*;
import com.ys.tmall.mapper.OrderMapper;
import com.ys.tmall.service.OrderItemService;
import com.ys.tmall.service.OrderService;
import com.ys.tmall.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaosh
 * @data 2019/7/6
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ProductImageService productImageService;

    @Override
    public void add(Order order) {
        orderMapper.insertSelective(order);
    }

    @Override
    public Order list(int id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        addOrderFIeld(order);
        return order;
    }

    //封装
    private void addOrderFIeld(Order order) {
        Integer id = order.getId();
        List<Orderitem> orderitems = orderItemService.listAllByOid(id);

        int totalNumber = 0;
        float total = 0;
        for(Orderitem orderitem : orderitems){
            Product product = orderitem.getProduct();

            List<Productimage> singleProductimages = productImageService.listAllByPidAndType(product.getId(), "single");
            Integer firstProductimageId = singleProductimages.get(0).getId();

            product.setFirstProductimageId(firstProductimageId);

            totalNumber += orderitem.getNumber();
            total += orderitem.getProduct().getPromoteprice()*orderitem.getNumber();
        }

        order.setOrderitems(orderitems);
        order.setTotalNumber(totalNumber);
        order.setTotal(total);
    }

    @Override
    public void update(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public List<Order> listAll() {
        List<Order> orders = orderMapper.selectByExample(null);
        for(Order order : orders){
            addOrderFIeld(order);
        }
        return orders;
    }

    @Override
    public void delete(int oid) {
        orderMapper.deleteByPrimaryKey(oid);
    }

    @Override
    public List<Order> listAllByUserID(int userID) {

        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andUidEqualTo(userID);
        List<Order> orders = orderMapper.selectByExample(orderExample);

        for(Order order : orders){
            addOrderFIeld(order);
        }
        return orders;
    }

}
