package com.karvis.review.repo;

import com.karvis.review.entity.ReviewEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ReviewRepository extends CrudRepository<ReviewEntity,Integer> {
    @Transactional
    List<ReviewEntity> findByProductId(int productId);
}
