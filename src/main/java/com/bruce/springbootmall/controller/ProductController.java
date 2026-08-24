package com.bruce.springbootmall.controller;

import com.bruce.springbootmall.constant.ProductCategory;
import com.bruce.springbootmall.dto.ProductQueryParams;
import com.bruce.springbootmall.dto.ProductRequest;
import com.bruce.springbootmall.model.Product;
import com.bruce.springbootmall.service.ProductService;
import com.bruce.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/*
API 介面層 / 請求入口 (Front Controller)
接收前端發送的 HTTP 請求，驗證參數後呼叫 Service 層處理，
最後將結果包裝成 ResponseEntity (狀態碼 + JSON Body) 回傳給前端。
*/
@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    // 查詢商品列表
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProduct(
            // 查詢條件 Filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,

            // 排序 Sorting
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,

            // 分頁 Pagination
            @RequestParam(defaultValue = "5") @Max(100) @Min(0) Integer top, //這邊的top指的是MSSQL的取前N筆資料
            @RequestParam(defaultValue = "0") @Min(0) Integer offset  //這邊的offset指的是MSSQL的分頁功能
            ) {

        // 將前端傳入的查詢參數(篩選 / 排序 / 分頁)組裝成 ProductQueryParams,交由 Service 層查詢商品
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setTop(top);
        productQueryParams.setOffset(offset);

        //取得 product List
       List<Product> productList = productService.getProducts(productQueryParams);

       // 取得 Product 總數
       Integer total = productService.countProduct(productQueryParams);

       //分頁
       Page<Product> page = new Page<>();
       page.setTop(top);
       page.setOffset(offset);
       page.setTotal(total);
       page.setResults(productList);

       return ResponseEntity.status(HttpStatus.OK).body(page);
    }


    //查詢商品
    @GetMapping("/products/{productsId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productsId) {
        Product product = productService.getProductById(productsId);

        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else  {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //新增商品
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Integer productId = productService.createProduct(productRequest);

        Product product =  productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }


    //修改商品
    @PutMapping("/products/{productsId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productsId,
                                                 @RequestBody @Valid ProductRequest productRequest) {
        // 檢查product 是否存在
        Product product = productService.getProductById(productsId);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    // 檢查商品的數據
    productService.updateProduct(productsId,productRequest);

    Product updatedProduct =  productService.getProductById(productsId);

    return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    //刪除商品
    @DeleteMapping("/products/{productsId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productsId) {
        productService.deleteProductById(productsId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
