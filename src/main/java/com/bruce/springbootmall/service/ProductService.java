package com.bruce.springbootmall.service;

import com.bruce.springbootmall.dto.ProductRequest;
import com.bruce.springbootmall.model.Product;

import java.time.Period;

/*
業務邏輯層 (Business Logic Layer) 的功能規格藍圖
定義所有與商品相關的「商業邏輯功能」。
作為 Controller 與 DAO 之間的橋樑，負責協調資料處理與業務規則。
*/
public interface ProductService {

    Product getProductById(Integer ProductId);

    Integer createProduct(ProductRequest productRequest);
}
