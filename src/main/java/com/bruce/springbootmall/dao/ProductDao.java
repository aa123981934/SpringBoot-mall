package com.bruce.springbootmall.dao;

import com.bruce.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer ProductId);
}
