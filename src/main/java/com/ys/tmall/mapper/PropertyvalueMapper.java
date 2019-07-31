package com.ys.tmall.mapper;

import com.ys.tmall.bean.Propertyvalue;
import com.ys.tmall.bean.PropertyvalueExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PropertyvalueMapper {
    long countByExample(PropertyvalueExample example);

    int deleteByExample(PropertyvalueExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Propertyvalue record);

    int insertSelective(Propertyvalue record);

    List<Propertyvalue> selectByExample(PropertyvalueExample example);

    Propertyvalue selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Propertyvalue record, @Param("example") PropertyvalueExample example);

    int updateByExample(@Param("record") Propertyvalue record, @Param("example") PropertyvalueExample example);

    int updateByPrimaryKeySelective(Propertyvalue record);

    int updateByPrimaryKey(Propertyvalue record);
}