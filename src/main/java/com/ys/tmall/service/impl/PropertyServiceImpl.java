package com.ys.tmall.service.impl;

import com.ys.tmall.bean.Property;
import com.ys.tmall.bean.PropertyExample;
import com.ys.tmall.mapper.PropertyMapper;
import com.ys.tmall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

;

/**
 * @author yaosh
 * @data 2019/7/6
 */
@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    PropertyMapper propertyMapper;

    @Override
    public int create(Property property) {
        return propertyMapper.insertSelective(property);
    }

    @Override
    public int update(Property property) {
        return propertyMapper.updateByPrimaryKeySelective(property);
    }

    @Override
    public int delete(int id) {
        return propertyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Property list(int id) {
        return propertyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Property> listAllByCid(int cid) {
        PropertyExample example = new PropertyExample();
        PropertyExample.Criteria criteria = example.createCriteria();
        criteria.andCidEqualTo(cid);
        return propertyMapper.selectByExample(example);
    }

}
