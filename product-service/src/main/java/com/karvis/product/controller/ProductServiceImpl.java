package com.karvis.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.karvis.api.product.Product;
import com.karvis.api.product.ProductService;
import com.karvis.util.http.ServiceUtil;

@RestController
public class ProductServiceImpl implements ProductService{

	private final ServiceUtil serviceUtil;
	
	
	@Autowired
	public ProductServiceImpl(ServiceUtil serviceUtil) {
		// TODO Auto-generated constructor stub
		this.serviceUtil = serviceUtil;
	}
	@Override
	public Product getProduct(int productId) {
		// TODO Auto-generated method stub
		return new Product(productId, "name-"+productId, 123, serviceUtil.getServiceAddress());
	}

}
