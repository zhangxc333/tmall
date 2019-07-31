package com.ys.tmall.service;

import com.ys.tmall.bean.Property;

import java.util.List;

/**
 * @author yaosh
 * @data 2019/7/6
 */
public interface PropertyService {
    int create(Property property);

    int update(Property property);

    int delete(int id);

    Property list(int id);

    List<Property> listAllByCid(int cid);

}
