package com.dimas.product.repository;

import com.dimas.product.entity.ERole;
import com.dimas.product.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleEntityRepository extends JpaRepository<RoleEntity,Long> {

    Optional<RoleEntity> findByName (ERole name);

}
