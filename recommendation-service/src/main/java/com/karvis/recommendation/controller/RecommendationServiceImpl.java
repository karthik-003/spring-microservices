package com.karvis.recommendation.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.karvis.api.recommendation.Recommendation;
import com.karvis.api.recommendation.RecommendationService;
import com.karvis.util.http.ServiceUtil;

@RestController
public class RecommendationServiceImpl implements RecommendationService{

	@Autowired
	ServiceUtil util;

	@Override
	public List<Recommendation> getRecommendations(int productId) {
		
		List<Recommendation> recomList = Arrays.asList(
				new Recommendation(productId, 1, "Karvis", 3, "New Recommendation", util.getServiceAddress())
		);
		// TODO Auto-generated method stub
		return recomList;
	}
	
	
}
