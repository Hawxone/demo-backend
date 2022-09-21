package com.dimas.product.service;

import com.dimas.product.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProduct();

    Product addProduct(Product saveProduct) throws Exception;

    Product getProductById(String id);

    Product updateProduct(String id, Product product);

    boolean deleteProduct(String id);

    void ImportProducts(List<Product> productList) throws Exception;
}
