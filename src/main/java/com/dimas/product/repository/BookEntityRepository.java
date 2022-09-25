package com.dimas.product.repository;

import com.dimas.product.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookEntityRepository extends JpaRepository<BookEntity,String> {

    Optional<BookEntity> findBookEntityByImageOrder(Integer imageOrder);
}
