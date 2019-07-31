package com.ys.tmall.service;

import com.ys.tmall.bean.Order;

import java.util.List;

/**
 * @author yaosh
 * @data 2019/7/6
 */
public interface OrderService {

    void add(Order order);

    Order list(int id);

    void update(Order order);

    List<Order> listAll();

    void delete(int oid);

    List<Order> listAllByUserID(int userID);
}
