package com.karvis.composite.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.karvis.api.composite.product.ProductAggregate;
import com.karvis.api.composite.product.ProductCompositeService;
import com.karvis.api.composite.product.RecommendationSummary;
import com.karvis.api.composite.product.ReviewSummary;
import com.karvis.api.composite.product.ServiceAddresses;
import com.karvis.api.product.Product;
import com.karvis.api.recommendation.Recommendation;
import com.karvis.api.review.Review;
import com.karvis.composite.service.ProductCompositeIntegration;
import com.karvis.util.http.ServiceUtil;

@RestController
public class ProductCompositeSeviceImpl implements ProductCompositeService{

	private final ServiceUtil serviceUtil;
	private ProductCompositeIntegration intgService;
	
	@Autowired
	public ProductCompositeSeviceImpl(ServiceUtil serviceUtil,ProductCompositeIntegration intgService) {
		// TODO Auto-generated constructor stub
		this.serviceUtil = serviceUtil;
		this.intgService = intgService;
	}
	@Override
	public ProductAggregate getProduct(int productId) {
		// TODO Auto-generated method stub
		Product product = intgService.getProduct(productId);
		List<Review> reviews = intgService.getReviews(productId);
		List<Recommendation> recommendations = intgService.getRecommendations(productId);
		
		
		return createProductAggregate(product,recommendations,reviews);
	}
	
	private ProductAggregate createProductAggregate(Product p, List<Recommendation> recommendations,
			List<Review> reviews){
		
		List<RecommendationSummary> recommendationSummary = (recommendations==null) ? null :
			recommendations.stream()
			.map(r -> new RecommendationSummary(r.getRecommendationId(), r.getAuthor(), r.getRate()))
			.collect(Collectors.toList());
		
		List<ReviewSummary> reviewSummary = (reviews == null && reviews.size()>0) ? null :
			reviews.stream()
			.map(r -> new ReviewSummary(r.getReviewId(), r.getAuthor(), r.getSubject()))
			.collect(Collectors.toList());
		
		String reviewAdrr = (reviews !=  null && reviews.size()>0) ? reviews.get(0).getServiceAddress(): "";
		String reommendationAddr = (recommendations!=null && recommendations.size()>0) ? recommendations.get(0).getServiceAddress():"";
		
		ServiceAddresses serviceAddr = new ServiceAddresses(serviceUtil.getServiceAddress(), p.getServiceAddress(), reviewAdrr, reommendationAddr);
		
		return new ProductAggregate(p.getProductId(), p.getName(), p.getWeight(), recommendationSummary, reviewSummary, serviceAddr);
	}

}
