package com.ys.tmall.service;

import com.ys.tmall.bean.Review;

import java.util.List;

/**
 * @author yaosh
 * @data 2019/7/18
 */
public interface ReviewService {

    List<Review> listByPid(int pid);

    int countProductByPid(int pid);

    void add(Review review);
}
