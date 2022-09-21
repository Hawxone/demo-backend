package com.dimas.product.repository;

import com.dimas.product.entity.CategoryEntity;
import com.dimas.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryEntityRepository extends JpaRepository<CategoryEntity,String> {
}
