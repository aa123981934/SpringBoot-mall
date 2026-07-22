package com.bruce.springbootmall.rowmapper;

import com.bruce.springbootmall.constant.ProductCategory;
import com.bruce.springbootmall.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
/*
RowMapper 表格轉物件
作用：扮演資料庫與 Java 物件之間的「翻譯官」。
負責將資料庫查詢結果集 (ResultSet) 的每一列數據，逐一轉換並封裝成 Java 的 Product 模型物件。
*/
public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();

        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));

        //字串轉換euum類型
        String category = rs.getString("category");
        ProductCategory productCategory = ProductCategory.valueOf(category);
        product.setCategory(productCategory);


        product.setImageUrl(rs.getString("image_url"));
        product.setPrice(rs.getInt("price"));
        product.setStock(rs.getInt("stock"));
        product.setDescription(rs.getString("description"));
        product.setCreateDate(rs.getTimestamp("created_date"));
        product.setLast_Modified_date(rs.getTimestamp("last_modified_date"));

        return  product;
    }
}
