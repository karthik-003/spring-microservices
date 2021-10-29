package com.karvis.recommendation.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.karvis.api.product.Product;
import com.karvis.recommendation.entity.RecommendationEntity;
import com.karvis.recommendation.mapper.RecommendationMapper;
import com.karvis.recommendation.repo.RecommendationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.karvis.api.recommendation.Recommendation;
import com.karvis.api.recommendation.RecommendationService;
import com.karvis.util.http.ServiceUtil;

@RestController
public class RecommendationServiceImpl implements RecommendationService{


	@Autowired
	RecommendationMapper mapper;
	@Autowired
	RecommendationRepository repository;

	Logger logger = LoggerFactory.getLogger(RecommendationServiceImpl.class);

	@Override
	public List<Recommendation> getRecommendations(int productId) {
		logger.info("Fetching all recommendations for productId: "+productId);
		List<Recommendation> recommendations = null;
		try{
			List<RecommendationEntity> recomList = repository.findByProductId(productId);
			logger.info("Total Recommendations for product: "+recomList.size());
			recommendations = recomList.stream()
					.map(e->mapper.entityToApi(e))
					.collect(Collectors.toList());
		}catch (Exception e){
			logger.error("Error while fetching recommendation for productId: ("+productId+"): "+e.getMessage());
		}

		return recommendations;
	}

	@Override
	public Recommendation createRecommendation(Recommendation body) {
		logger.info("Creating recommendation for productId: "+body.getProductId());
		Recommendation response = null;
		try {
			RecommendationEntity entity = mapper.apiToEntity(body);
			RecommendationEntity savedEntity = repository.save(entity);
			response = mapper.entityToApi(savedEntity);
		}catch (Exception e){
			logger.error("Error while creating recommendation for productId: ("+body.getProductId()+"): "+e.getMessage());
		}
		return response;
	}

	@Override
	public void deleteRecommendations(int productId) {
		logger.info("Deleting all recommendations for productId: "+productId);
		try{
			List<RecommendationEntity> recommendations = repository.findByProductId(productId);
			repository.deleteAll(recommendations);
		}catch (Exception e){
			logger.error("Error while deleting recommendation for productId: ("+productId+"): "+e.getMessage());
		}
	}


}
