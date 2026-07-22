package com.bruce.springbootmall.dao;

import com.bruce.springbootmall.dto.ProductRequest;
import com.bruce.springbootmall.model.Product;


/*
*DAO(資料存取物件)
作用：定義與資料庫 (product 資料表) 進行交互的方法規格。
負責處理所有資料庫層面的 CRUD (增刪改查) 操作。
* */
public interface ProductDao {

    Product getProductById(Integer ProductId);

    Integer createProduct(ProductRequest productRequest);
}
