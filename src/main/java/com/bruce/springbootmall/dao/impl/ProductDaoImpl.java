package com.bruce.springbootmall.dao.impl;

import com.bruce.springbootmall.dao.ProductDao;
import com.bruce.springbootmall.dto.ProductQueryParams;
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

    // 查詢商品列表
    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date " +
                "FROM product WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        if (productQueryParams.getCategory() != null) {
            sql = sql +" AND category = :category";
            map.put("category", productQueryParams.getCategory().name());
        }

        if (productQueryParams.getSearch() != null) {
            sql = sql + " AND product_name LIKE :search";
            map.put("search", "%" + productQueryParams.getSearch() + "%");
        }

        sql = sql + " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return productList;
    }

    //查詢商品
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
    //新增商品
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
    // 修改商品
    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl, " +
                "price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate " +
                "WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "delete from product where product_id = :productId";

        Map<String,Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }
}
