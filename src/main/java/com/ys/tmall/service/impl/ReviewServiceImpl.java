package com.ys.tmall.service.impl;

import com.ys.tmall.bean.Review;
import com.ys.tmall.bean.ReviewExample;
import com.ys.tmall.mapper.ReviewMapper;
import com.ys.tmall.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaosh
 * @data 2019/7/18
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    @Override
    public List<Review> listByPid(int pid) {
        ReviewExample reviewExample = new ReviewExample();
        ReviewExample.Criteria criteria = reviewExample.createCriteria();
        criteria.andPidEqualTo(pid);
        return reviewMapper.selectByExample(reviewExample);
    }

    @Override
    public int countProductByPid(int pid) {
        List<Review> reviews = listByPid(pid);
        int size = reviews.size();
        return size;
    }

    @Override
    public void add(Review review) {
        reviewMapper.insertSelective(review);
    }
}
