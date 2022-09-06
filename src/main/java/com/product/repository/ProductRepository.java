package com.product.repository;

import com.product.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, BigInteger>{

    @Query(value = "select * from products where name !~* ?1", nativeQuery = true)
    Page<ProductEntity> findAllByNameRegex(String name, Pageable pageable);

    List<ProductEntity> findAll(Specification spec, Pageable pageable);
}
