package com.ys.tmall.service;

import com.ys.tmall.bean.Propertyvalue;

import java.util.List;

/**
 * @author yaosh
 * @data 2019/7/6
 */
public interface PropertyValueService {

    int update(Propertyvalue propertyvalue);

    Propertyvalue list(int id);

    List<Propertyvalue> listByPid(int pid);

}
