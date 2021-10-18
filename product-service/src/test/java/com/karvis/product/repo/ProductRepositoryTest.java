package com.karvis.product.repo;


import com.karvis.product.entity.ProductEntity;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    private ProductEntity productEntity;
    private ProductEntity savedEntity;

    @Test
    public void testHello(){
        String sample = "hello";

        assertEquals(sample,new String("hello"));
    }


    @BeforeEach
    public void setupDb(){
        productRepository.deleteAll();
        ProductEntity entity = new ProductEntity(1,"Phone",300);
        savedEntity = productRepository.save(entity);
        assertEquals(entity,savedEntity);
    }

    private void assertEqualsProduct(ProductEntity expectedEntity, ProductEntity actualEntity) {
        assertEquals(expectedEntity.getId(),               actualEntity.getId());
        assertEquals(expectedEntity.getVersion(),          actualEntity.getVersion());
        assertEquals(expectedEntity.getProductId(),        actualEntity.getProductId());
        assertEquals(expectedEntity.getName(),           actualEntity.getName());
        assertEquals(expectedEntity.getWeight(),           actualEntity.getWeight());
    }
    @Test
    public void create() {
        ProductEntity newEntity = new ProductEntity(2, "n", 2);
        savedEntity = productRepository.save(newEntity);

        ProductEntity foundEntity =
                productRepository.findById(newEntity.getId()).get();
        assertEqualsProduct(newEntity, foundEntity);

        assertEquals(2, productRepository.count());
    }

    @Test
    public void update(){
        savedEntity.setName("n2");
        productRepository.save(savedEntity);

        ProductEntity entity = productRepository.findById(savedEntity.getId()).get();
        assertEquals(1, (long)entity.getVersion());
        assertEquals("n2",entity.getName());
        assertEquals(entity.getName(),savedEntity.getName());
    }

    @Test
    public void delete() {
        productRepository.delete(savedEntity);
        assertFalse(productRepository.existsById(savedEntity.getId()));
    }

    @Test
    public void getByProductId() {
        Optional<ProductEntity> entity =
                productRepository.findByProductId(savedEntity.getProductId());
        assertTrue(entity.isPresent());
        assertEqualsProduct(savedEntity, entity.get());
    }


    //@Test
    public void duplicateError() {
        System.out.println("**** existing id: "+savedEntity.getProductId());
        ProductEntity entity = new
                ProductEntity(savedEntity.getProductId(), "n", 1);
        Exception ex = assertThrows(DuplicateKeyException.class,()->{
            System.out.println("********* saving entity with: "+entity.getProductId());
            ProductEntity _entity = productRepository.save(entity);
            System.out.println("********* saved entity with: "+_entity.getProductId());
        });
        System.out.println(ex.getMessage());
        assertTrue(true);

    }
}
