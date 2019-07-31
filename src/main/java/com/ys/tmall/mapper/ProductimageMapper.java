package com.ys.tmall.mapper;

import com.ys.tmall.bean.Productimage;
import com.ys.tmall.bean.ProductimageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductimageMapper {
    long countByExample(ProductimageExample example);

    int deleteByExample(ProductimageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Productimage record);

    int insertSelective(Productimage record);

    List<Productimage> selectByExample(ProductimageExample example);

    Productimage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Productimage record, @Param("example") ProductimageExample example);

    int updateByExample(@Param("record") Productimage record, @Param("example") ProductimageExample example);

    int updateByPrimaryKeySelective(Productimage record);

    int updateByPrimaryKey(Productimage record);
}