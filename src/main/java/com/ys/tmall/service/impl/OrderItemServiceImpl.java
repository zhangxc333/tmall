package com.ys.tmall.service.impl;

import com.ys.tmall.bean.Orderitem;
import com.ys.tmall.bean.OrderitemExample;
import com.ys.tmall.bean.Product;
import com.ys.tmall.bean.Productimage;
import com.ys.tmall.mapper.OrderitemMapper;
import com.ys.tmall.mapper.ProductMapper;
import com.ys.tmall.service.OrderItemService;
import com.ys.tmall.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaosh
 * @data 2019/7/6
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderitemMapper orderitemMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    ProductServiceImpl productServiceImpl;

    @Override
    public void delete(int id) {
        orderitemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void add(Orderitem orderitem) {
        orderitemMapper.insertSelective(orderitem);
    }

    @Override
    public void update(Orderitem orderitem) {
        orderitemMapper.updateByPrimaryKeySelective(orderitem);
    }

    @Override
    public Orderitem list(int id) {
        Orderitem orderitem = orderitemMapper.selectByPrimaryKey(id);
        Product product = orderitem.getProduct();

        productServiceImpl.addField(product);

        return orderitem;
    }

    @Override
    public List<Orderitem> listAllByOid(int oid) {
        OrderitemExample example = new OrderitemExample();
        OrderitemExample.Criteria criteria = example.createCriteria();
        criteria.andOidEqualTo(oid);
        return orderitemMapper.selectWithProductByExample(example);
    }

    @Override
    public List<Orderitem> listAllByPid(int pid) {

        OrderitemExample orderitemExample = new OrderitemExample();
        OrderitemExample.Criteria criteria = orderitemExample.createCriteria();
        criteria.andPidEqualTo(pid);

        return orderitemMapper.selectByExample(orderitemExample);

    }

    @Override
    public List<Orderitem> listAllByUid(int uid) {
        OrderitemExample orderitemExample = new OrderitemExample();
        OrderitemExample.Criteria criteria = orderitemExample.createCriteria();
        criteria.andUidEqualTo(uid).andOidIsNull();

        List<Orderitem> orderitems = orderitemMapper.selectByExample(orderitemExample);

        for(Orderitem orderitem : orderitems){

            Product product = orderitem.getProduct();

            productServiceImpl.addField(product);

        }
        return orderitems;
    }

}
