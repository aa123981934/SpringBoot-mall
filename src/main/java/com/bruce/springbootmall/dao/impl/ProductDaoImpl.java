package com.bruce.springbootmall.dao.impl;

import com.bruce.springbootmall.dao.ProductDao;
import com.bruce.springbootmall.dto.ProductRequest;
import com.bruce.springbootmall.model.Product;
import com.bruce.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
作用：實作 ProductDao 介面，使用 NamedParameterJdbcTemplate，撰寫原生 SQL 語法，
負責真正對資料庫product 資料表進行讀取與操作
*/
@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer ProductId) {

        String sql = "select product_id,product_name, category, image_url, price, stock, description, created_date, last_modified_date " +
                "from product where product_id = :productId";

        Map<String,Object> map = new HashMap<>();
        map.put("productId", ProductId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if(productList.size() > 0){
            return productList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {

        String sql = "insert into product(product_name, category,image_url, price, stock, description, created_date, last_modified_date)" +
                "values (:productName, :category,:image_url, :price, :stock, :description, :created_date, :last_modified_date)";

        Map<String,Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("image_url", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("created_date", now);
        map.put("last_modified_date", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

        int productId = keyHolder.getKey().intValue();

        return productId;
    }
}
