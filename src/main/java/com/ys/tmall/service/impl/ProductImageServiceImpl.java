package com.ys.tmall.service.impl;

import com.ys.tmall.bean.Productimage;
import com.ys.tmall.bean.ProductimageExample;
import com.ys.tmall.mapper.ProductimageMapper;
import com.ys.tmall.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

;

/**
 * @author yaosh
 * @data 2019/7/6
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    ProductimageMapper productimageMapper;

    @Override
    public int create(Productimage productimage) {
        return productimageMapper.insertSelective(productimage);
    }

    @Override
    public int delete(int id) {
        return productimageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Productimage list(int id) {
        return productimageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Productimage> listAllByPidAndType(int pid , String type) {
        ProductimageExample example = new ProductimageExample();
        ProductimageExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid).andTypeEqualTo(type);
        return productimageMapper.selectByExample(example);
    }

}
