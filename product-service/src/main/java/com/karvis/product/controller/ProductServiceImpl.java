package com.karvis.product.controller;

import com.karvis.product.entity.ProductEntity;
import com.karvis.product.mapper.ProductMapper;
import com.karvis.product.repo.ProductRepository;
import com.karvis.util.exception.InvalidInputException;
import com.karvis.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.karvis.api.product.Product;
import com.karvis.api.product.ProductService;
import com.karvis.util.http.ServiceUtil;

@RestController
public class ProductServiceImpl implements ProductService{

	private final ServiceUtil serviceUtil;
	private final ProductRepository repo;
	private final ProductMapper mapper;

	Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Autowired
	public ProductServiceImpl(ServiceUtil serviceUtil,ProductRepository repo,ProductMapper mapper) {
		// TODO Auto-generated constructor stub
		this.serviceUtil = serviceUtil;
		this.repo = repo;
		this.mapper = mapper;
	}
	@Override
	public Product getProduct(int productId) {
		// TODO Auto-generated method stub
		if(productId < 1) throw new InvalidInputException("Invalid productId: "+productId);
		ProductEntity entity = repo.findByProductId(productId)
				.orElseThrow(()->new NotFoundException("No product found with id: "+productId));
		Product response = mapper.entityToApi(entity);
		return response;

	}

	@Override
	public Product createProduct(Product body) {
		logger.info("Creating product with ID: "+body.getProductId()+" and name: "+body.getName());
		try {
			ProductEntity entity = mapper.apiToEntity(body);
			ProductEntity newEntity = repo.save(entity);
			logger.debug("before entityToAPi: Created product with ID: "+newEntity.getProductId()+" and name: "+newEntity.getName());
			Product response = mapper.entityToApi(newEntity);
			logger.debug("Created product with ID: "+response.getProductId()+" and name: "+response.getName());
			return response;
		}catch (Exception e){
			logger.error("Exception while creating prodcut: "+e.getMessage());
			throw  e;
		}


	}

	@Override
	public void deleteProduct(int productId) {
		repo.findByProductId(productId).ifPresent(e->repo.delete(e));
	}

}
