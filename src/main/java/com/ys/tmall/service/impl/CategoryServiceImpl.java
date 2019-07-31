package com.ys.tmall.service.impl;

import com.ys.tmall.bean.Category;
import com.ys.tmall.bean.Product;
import com.ys.tmall.mapper.CategoryMapper;
import com.ys.tmall.mapper.ProductMapper;
import com.ys.tmall.mapper.ProductimageMapper;
import com.ys.tmall.service.CategoryService;
import com.ys.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaosh
 * @data 2019/7/6
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductimageMapper productimageMapper;
    @Autowired
    ProductService productService;

    @Override
    public int create(Category category) {
        return categoryMapper.insertSelective(category);
    }

    @Override
    public int update(Category category) {
        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public int delete(int id) {
        return categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Category list(int id) {
        Category category = categoryMapper.selectByPrimaryKey(id);

        //1、遍历分类的集合，并取出每个分类的id
        Integer cid = category.getId();
        //2、根据分类的id，查出该分类对应的产品
        List<Product> products = productService.listAllByCid(cid);
        //3、将查询到的结果添加到分类中
        category.setProducts(products);

        return category;
    }

    @Override
    public List<Category> listAll() {
        List<Category> categories = categoryMapper.selectByExample(null);

        for (Category category : categories) {
            //1、遍历分类的集合，并取出每个分类的id
            Integer id = category.getId();
            //2、根据分类的id，查出该分类对应的产品
            List<Product> products = productService.listAllByCid(id);
            //3、将查询到的结果添加到分类中
            category.setProducts(products);
        }

        return categories;
    }


}
