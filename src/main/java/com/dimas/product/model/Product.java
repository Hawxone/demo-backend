package com.dimas.product.model;


import com.dimas.product.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String id;
    private String name;
    private String categoryId;
    private String category;
    private Double price;
    private String timeStamp;

}
