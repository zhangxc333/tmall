package com.ys.tmall.service.impl;

import com.ys.tmall.bean.*;
import com.ys.tmall.mapper.ProductMapper;
import com.ys.tmall.mapper.ProductimageMapper;
import com.ys.tmall.service.OrderItemService;
import com.ys.tmall.service.ProductImageService;
import com.ys.tmall.service.ProductService;
import com.ys.tmall.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

;

/**
 * @author yaosh
 * @data 2019/7/6
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductimageMapper productimageMapper;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    ProductImageService productImageService;

    @Override
    public int create(Product product) {
        return productMapper.insertSelective(product);
    }

    @Override
    public int update(Product product) {
        return productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public int delete(int id) {
        return productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Product list(int id) {
        Product product = productMapper.selectByPrimaryKey(id);
        addField(product);
        return product;
    }

    @Override
    public List<Product> listAllByCid(int cid) {
        ProductExample example = new ProductExample();
        ProductExample.Criteria criteria = example.createCriteria();
        criteria.andCidEqualTo(cid);

        List<Product> products = productMapper.selectByExample(example);

        for (Product product : products) {
            addField(product);
        }
        return products;
    }

    @Override
    public List<Product> listForSearch(String keyword) {
        ProductExample productExample = new ProductExample();
//        productExample.setOrderByClause("id desc");
        ProductExample.Criteria criteria = productExample.createCriteria();
        if(keyword != null){
            criteria.andNameLike("%" + keyword + "%");
        }
        List<Product> products = productMapper.selectByExample(productExample);
        for (Product product : products) {
            addField(product);
        }
        return products;
    }

    //用于给product新增的属性赋值
    public void addField(Product product){

        int id = product.getId();

        //添加saleCount
        int saleCount = 0;
        List<Orderitem> orderitems = orderItemService.listAllByPid(id);
        for(Orderitem orderitem : orderitems){
            if(orderitem.getOrder() != null && orderitem.getOrder().getPaydate() != null){
                saleCount += orderitem.getNumber();
            }
        }
        product.setSaleCount(saleCount);

        //对应的产品图片
        List<Productimage> singleProductimages = productImageService.listAllByPidAndType(id, "single");
        List<Productimage> detailProductimages = productImageService.listAllByPidAndType(id, "detail");
        product.setSingleProductimages(singleProductimages);
        product.setDetailProductimages(detailProductimages);

        //添加firstProductimageId
        product.setFirstProductimageId(singleProductimages.get(0).getId());

        //设置产品评价数量reviewNumble
        int reviewNumble = reviewService.countProductByPid(id);
        product.setReviewCount(reviewNumble);

    }
}
