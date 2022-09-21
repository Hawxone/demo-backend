package com.dimas.product.repository;

import com.dimas.product.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BlogEntityRepository  extends JpaRepository<BlogEntity,String> {
}
