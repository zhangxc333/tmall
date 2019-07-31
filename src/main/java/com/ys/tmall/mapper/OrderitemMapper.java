package com.ys.tmall.mapper;

import com.ys.tmall.bean.Orderitem;
import com.ys.tmall.bean.OrderitemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderitemMapper {
    long countByExample(OrderitemExample example);

    int deleteByExample(OrderitemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Orderitem record);

    int insertSelective(Orderitem record);

    List<Orderitem> selectByExample(OrderitemExample example);

    //新增
    List<Orderitem> selectWithProductByExample(OrderitemExample example);

    Orderitem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Orderitem record, @Param("example") OrderitemExample example);

    int updateByExample(@Param("record") Orderitem record, @Param("example") OrderitemExample example);

    int updateByPrimaryKeySelective(Orderitem record);

    int updateByPrimaryKey(Orderitem record);
}