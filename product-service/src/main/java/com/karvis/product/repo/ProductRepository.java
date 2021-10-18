package com.karvis.product.repo;


import com.karvis.product.entity.ProductEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity,String> {
    Optional<ProductEntity> findByProductId(int productId);
}
