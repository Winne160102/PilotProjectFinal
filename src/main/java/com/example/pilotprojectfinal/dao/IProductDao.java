package com.example.pilotprojectfinal.dao;

import com.example.pilotprojectfinal.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductDao extends JpaRepository<ProductEntity, Long> {

    ProductEntity findByProductId(Long productId);

    ProductEntity findByProductName(String productName);

    ProductEntity findByProductNameAndProductIdNot(String productName, Long productId);

    Page<ProductEntity> findByProductNameLike(String productName, Pageable pageable);
}