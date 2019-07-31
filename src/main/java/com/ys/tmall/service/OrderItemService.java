package com.ys.tmall.service;

import com.ys.tmall.bean.Orderitem;

import java.util.List;

/**
 * @author yaosh
 * @data 2019/7/6
 */
public interface OrderItemService {

    void delete(int id);

    void add(Orderitem orderitem);

    void update(Orderitem orderitem);

    Orderitem list(int id);

    List<Orderitem> listAllByOid(int oid);

    List<Orderitem> listAllByPid(int pid);

    List<Orderitem> listAllByUid(int uid);

}
