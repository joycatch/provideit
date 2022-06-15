package com.provideit.assignment.repository;

import com.provideit.assignment.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value="SELECT * FROM PRODUCT WHERE price >= :min AND price <= :max", nativeQuery=true)
    Page<Product> findByMinAndMaxPrice(@Param("min") double min, @Param("max") double max, Pageable pageable);

    @Query(value="SELECT * FROM PRODUCT WHERE category = :category", nativeQuery=true)
    Page<Product> findByCategory(@Param("category") String category, Pageable pageable);

}
