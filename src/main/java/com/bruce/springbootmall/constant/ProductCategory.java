package com.bruce.springbootmall.constant;


/*
商品分類列舉 (Enum)
作用：定義系統中「商品分類」的固定選項常數。
用於限制與規範 Product 與 ProductRequest 中的 category 欄位值，避免資料不一致。
*/
public enum ProductCategory {
    FOOD,
    CAR,
    BOOK,
    PHONE
}
