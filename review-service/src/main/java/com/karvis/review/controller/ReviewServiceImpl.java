package com.karvis.review.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.karvis.api.review.Review;
import com.karvis.api.review.ReviewService;
import com.karvis.util.http.ServiceUtil;

@RestController
public class ReviewServiceImpl implements ReviewService{

	
	ServiceUtil serviceUtil;
	
	@Autowired
	public ReviewServiceImpl(ServiceUtil serviceUtil) {
		// TODO Auto-generated constructor stub
		this.serviceUtil = serviceUtil;
	}
	@Override
	public List<Review> getReviews(int productId) {
		// TODO Auto-generated method stub
		
		List<Review> reviews = Arrays.asList(
				new Review(productId, 1, "Karvis", "Good product", "Really good product for the price.", serviceUtil.getServiceAddress()),
				new Review(productId, 2, "Karvis", "Good product", "Really good product for the price.", serviceUtil.getServiceAddress())
				);
		
		return reviews;
	}

}
