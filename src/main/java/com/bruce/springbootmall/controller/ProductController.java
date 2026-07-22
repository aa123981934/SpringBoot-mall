package com.bruce.springbootmall.controller;

import com.bruce.springbootmall.dto.ProductRequest;
import com.bruce.springbootmall.model.Product;
import com.bruce.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
API 介面層 / 請求入口 (Front Controller)
接收前端發送的 HTTP 請求，驗證參數後呼叫 Service 層處理，
最後將結果包裝成 ResponseEntity (狀態碼 + JSON Body) 回傳給前端。
*/
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productsId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productsId) {
        Product product = productService.getProductById(productsId);

        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else  {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Integer productId = productService.createProduct(productRequest);

        Product product =  productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}
