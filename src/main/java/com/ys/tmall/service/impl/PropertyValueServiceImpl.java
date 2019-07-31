package com.ys.tmall.service.impl;

import com.ys.tmall.bean.Propertyvalue;
import com.ys.tmall.bean.PropertyvalueExample;
import com.ys.tmall.mapper.PropertyvalueMapper;
import com.ys.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

;

/**
 * @author yaosh
 * @data 2019/7/6
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    PropertyvalueMapper propertyvalueMapper;

    @Override
    public int update(Propertyvalue propertyvalue) {
        return propertyvalueMapper.updateByPrimaryKeySelective(propertyvalue);
    }

    @Override
    public Propertyvalue list(int id) {
        return propertyvalueMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Propertyvalue> listByPid(int pid) {
        PropertyvalueExample propertyvalueExample = new PropertyvalueExample();
        PropertyvalueExample.Criteria criteria = propertyvalueExample.createCriteria();
        criteria.andPidEqualTo(pid);
        return propertyvalueMapper.selectByExample(propertyvalueExample);
    }

}
