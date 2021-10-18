package com.karvis.recommendation.repo;

import com.karvis.recommendation.entity.RecommendationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecommendationRepository extends CrudRepository<RecommendationEntity,Integer> {
    List<RecommendationEntity> findByProductId(int productId);
}
