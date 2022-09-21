package com.dimas.product.repository;

import com.dimas.product.entity.ProductEntity;
import com.dimas.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEntityRepository extends JpaRepository<ProductEntity,String> {


}
