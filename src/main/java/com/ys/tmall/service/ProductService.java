package com.ys.tmall.service;

import com.ys.tmall.bean.Product;

import java.util.List;

/**
 * @author yaosh
 * @data 2019/7/6
 */
public interface ProductService {
    int create(Product product);

    int update(Product product);

    int delete(int id);

    Product list(int id);

    List<Product> listAllByCid(int cid);

    List<Product> listForSearch(String keyword);

}