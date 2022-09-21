package com.dimas.product.service;

import com.dimas.product.entity.CategoryEntity;
import com.dimas.product.entity.ProductEntity;
import com.dimas.product.model.Product;
import com.dimas.product.repository.CategoryEntityRepository;
import com.dimas.product.repository.ProductEntityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductEntityRepository productEntityRepository;
    private CategoryEntityRepository categoryEntityRepository;

    public ProductServiceImpl(ProductEntityRepository productEntityRepository, CategoryEntityRepository categoryEntityRepository) {
        this.productEntityRepository = productEntityRepository;
        this.categoryEntityRepository = categoryEntityRepository;
    }



    @Override
    public List<Product> getProduct() {

        List<ProductEntity> productEntities = productEntityRepository.findAll();

        List<Product> products = new ArrayList<>();

        products = productEntities.stream().map((productEntity)->
                Product.builder()
                        .id(productEntity.getId())
                        .timeStamp(productEntity.getTimeStamp())
                        .name(productEntity.getName())
                        .price(productEntity.getPrice())
                        .categoryId(productEntity.getCategory().getId())
                        .category(productEntity.getCategory().getName())
                        .build())
                .collect(Collectors.toList());

        return products;
    }

    @Override
    public Product addProduct(Product saveProduct) throws Exception {
        try {
            CategoryEntity category = categoryEntityRepository.findById(saveProduct.getCategoryId()).get();

            ProductEntity productEntity = new ProductEntity();

            productEntity.setName(saveProduct.getName());
            productEntity.setPrice(saveProduct.getPrice());
            productEntity.setTimeStamp(saveProduct.getTimeStamp());
            productEntity.setCategory(category);
            productEntity.setId(saveProduct.getId());

            productEntity = productEntityRepository.save(productEntity);
            saveProduct.setId(productEntity.getId());
        }catch (Exception e){
            throw new Exception("could not save post : "+ e);
        }
        return saveProduct;
    }

    @Override
    public void ImportProducts(List<Product> productList) throws Exception {


        try{
            for (int index = 0; index < productList.size(); index++) {
                CategoryEntity category = categoryEntityRepository.findById(productList.get(index).getCategoryId()).get();
                ProductEntity productEntity = new ProductEntity();

                productEntity.setName(productList.get(index).getName());
                productEntity.setPrice(productList.get(index).getPrice());
                productEntity.setTimeStamp(productList.get(index).getTimeStamp());
                productEntity.setCategory(category);
                productEntity.setId(productList.get(index).getId());
                productEntity = productEntityRepository.save(productEntity);

                productList.get(index).setId(productEntity.getId());
            }


        }catch (Exception e){
            throw new Exception("could not save post : "+ e);
        }
    }


    @Override
    public Product getProductById(String id) {

       ProductEntity productEntity =  productEntityRepository.findById(id).get();

       Product product = new Product();

       product.setId(productEntity.getId());
       product.setPrice(productEntity.getPrice());
       product.setName(productEntity.getName());
       product.setCategory(productEntity.getCategory().getName());
       product.setCategoryId(productEntity.getCategory().getId());
       product.setTimeStamp(product.getTimeStamp());


        return product;
    }


    @Override
    public Product updateProduct(String id, Product product) {

        ProductEntity productEntity =  productEntityRepository.findById(id).get();
        CategoryEntity category = categoryEntityRepository.findById(product.getCategoryId()).get();

        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        productEntity.setTimeStamp(new Date().toString());
        productEntity.setCategory(category);

        productEntityRepository.save(productEntity);


        return product;
    }

    @Override
    public boolean deleteProduct(String id) {

        ProductEntity productEntity =  productEntityRepository.findById(id).get();

        productEntityRepository.delete(productEntity);


        return true;
    }


}
