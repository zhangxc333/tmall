package com.ys.tmall.comparator;

import com.ys.tmall.bean.Product;

import java.util.Comparator;

/**
 * @author yaosh
 * @data 2019/7/21
 */
public class ProductPriceComparator implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        return (int) (p2.getPromoteprice()-p1.getPromoteprice());
    }
}
