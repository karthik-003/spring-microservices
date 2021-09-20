package com.karvis.composite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karvis.api.product.Product;
import com.karvis.api.product.ProductService;
import com.karvis.api.recommendation.Recommendation;
import com.karvis.api.recommendation.RecommendationService;
import com.karvis.api.review.Review;
import com.karvis.api.review.ReviewService;

@Component
public class ProductCompositeIntegration implements ProductService,ReviewService,RecommendationService{

	
	private final RestTemplate restTemplate;
	private final ObjectMapper mapper;

	private final String productServiceHost;
	private final String recommendationServiceHost;
	private final String reviewServiceHost;
	
	private final int productServicePort;
	private final int reviewServicePort;
	private final int recommendationServicePort;
	
	private final String productServiceUrl;
	private final String reviewServiceUrl;
	private final String recommendationServiceUrl;
	
	private final String productRoot = "/product/";
	private final String reviewRoot = "/review?productId=";
	private final String recommendationRoot = "/recommendation?productId=";
	
	private final String HTTP = "http://";
	@Autowired
	public ProductCompositeIntegration(RestTemplate restTemplate, ObjectMapper mapper, 
			@Value("${app.product-service.host}") String productServiceHost,
			@Value("${app.recommendation-service.host}") String recommendationServiceHost, 
			@Value("${app.review-service.host}") String reviewServiceHost, 
			@Value("${app.product-service.port}") int productServicePort, 
			@Value("${app.review-service.port}") int reviewServicePort,
			@Value("${app.recommendation-service.port}") int recommendationServicePort) {
		
		this.restTemplate = restTemplate;
		this.mapper = mapper;
		this.productServiceHost = productServiceHost;
		this.recommendationServiceHost = recommendationServiceHost;
		this.reviewServiceHost = reviewServiceHost;
		this.productServicePort = productServicePort;
		this.reviewServicePort = reviewServicePort;
		this.recommendationServicePort = recommendationServicePort;
		
		this.productServiceUrl = this.HTTP+this.productServiceHost+":"+this.productServicePort+this.productRoot;
		this.reviewServiceUrl = this.HTTP+this.reviewServiceHost+":"+this.reviewServicePort+this.reviewRoot;
		this.recommendationServiceUrl = this.HTTP+this.recommendationServiceHost+":"+this.recommendationServicePort+this.recommendationRoot;
		
	}

	@Override
	public List<Recommendation> getRecommendations(int productId) {
		// TODO Auto-generated method stub
		String url = this.recommendationServiceUrl+productId;
		List<Recommendation> recommendations = restTemplate.exchange(url, HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Recommendation>>() {
				}).getBody();
		return recommendations;
	}

	@Override
	public List<Review> getReviews(int productId) {
		// TODO Auto-generated method stub
		String url = this.reviewServiceUrl+productId;
		List<Review> reviews = restTemplate.exchange(url, HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Review>>() {
				}).getBody();
		return reviews;
	}

	@Override
	public Product getProduct(int productId) {
		String url = this.productServiceUrl+productId;
		Product prod = restTemplate.getForObject(url, Product.class);
		return prod;
	}

}
