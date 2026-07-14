package com.bruce.springbootmall.service.impl;

import com.bruce.springbootmall.dao.ProductDao;
import com.bruce.springbootmall.model.Product;
import com.bruce.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer ProductId) {
        return productDao.getProductById(ProductId);
    }
}
