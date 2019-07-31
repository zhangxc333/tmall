package com.ys.tmall.service;

import com.ys.tmall.bean.Category;

import java.util.List;

/**
 * @author yaosh
 * @data 2019/7/6
 */
public interface CategoryService {
    int create(Category category);

    int update(Category category);

    int delete(int id);

    Category list(int id);

    List<Category> listAll();

}
