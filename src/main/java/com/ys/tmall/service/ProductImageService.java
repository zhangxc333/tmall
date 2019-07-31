package com.ys.tmall.service;

import com.ys.tmall.bean.Productimage;

import java.util.List;

/**
 * @author yaosh
 * @data 2019/7/6
 */
public interface ProductImageService {
    int create(Productimage productimage);

    int delete(int id);

    Productimage list(int id);

    List<Productimage> listAllByPidAndType(int pid, String type);

}
