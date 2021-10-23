package com.karvis.review.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.karvis.review.entity.ReviewEntity;
import com.karvis.review.mapper.ReviewMapper;
import com.karvis.review.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.karvis.api.review.Review;
import com.karvis.api.review.ReviewService;
import com.karvis.util.http.ServiceUtil;

@RestController
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	ServiceUtil serviceUtil;

	@Autowired
	ReviewRepository repository;
	@Autowired
	ReviewMapper mapper;

	/**
	 * Get reviews for a given product.
	 * @param productId
	 * @return
	 */
	@Override
	public List<Review> getReviews(int productId) {
		// TODO Auto-generated method stub
		List<Review> reviewsApi = null;
		List<ReviewEntity> reviewsEntity = repository.findByProductId(productId);
		if(reviewsEntity!=null && !reviewsEntity.isEmpty()){
			reviewsApi = new ArrayList<>();
			for(ReviewEntity _entity: reviewsEntity){
				reviewsApi.add(mapper.entityToApi(_entity));
			}
		}
		return reviewsApi;
	}

	/**
	 * Add a review for a product.
	 * @param body
	 * @return
	 */
	@Override
	public Review createReview(Review body) {
		ReviewEntity _entity = mapper.apiToEntity(body);
		_entity = repository.save(_entity);
		return mapper.entityToApi(_entity);
	}

	/**
	 * Find and delete all the reviews that are related to give productId.
	 * @param productId
	 */
	@Override
	public void deleteReviews(int productId) {
		List<ReviewEntity> productReviews = repository.findByProductId(productId);
		if(productReviews!=null &&!productReviews.isEmpty()){
			for(ReviewEntity _entity: productReviews){
				repository.delete(_entity);
			}

		}
	}

}
