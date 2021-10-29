package com.karvis.recommendation.persistance;

import com.karvis.recommendation.entity.RecommendationEntity;
import com.karvis.recommendation.repo.RecommendationRepository;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class PersistanceTests {

    @Autowired
    RecommendationRepository repository;

    RecommendationEntity savedEntity;

    @BeforeEach
    public void setupDb(){
        repository.deleteAll();
        RecommendationEntity entity =
                new RecommendationEntity(1,1,"Karvis",3,"Decent product");
        savedEntity = repository.save(entity);
    }

    private void assertEqualsRecommendation(RecommendationEntity expectedEntity,RecommendationEntity actualEntity){
        assertEquals(expectedEntity.getId(),actualEntity.getId());
        assertEquals(expectedEntity.getVersion(),actualEntity.getVersion());
        assertEquals(expectedEntity.getProductId(),actualEntity.getProductId());
        assertEquals(expectedEntity.getRecommendationId(),actualEntity.getRecommendationId());
        assertEquals(expectedEntity.getAuthor(),actualEntity.getAuthor());
        assertEquals(expectedEntity.getRating(),actualEntity.getRating());
        assertEquals(expectedEntity.getContent(),actualEntity.getContent());
    }

    @Test
    public void createRecommendation(){
        int productId = 1;
        RecommendationEntity entity =
                new RecommendationEntity(productId,2,"Karvis",3,"Decent product");
        RecommendationEntity createdEntity  = repository.save(entity);
        assertEqualsRecommendation(entity,createdEntity);
        List<RecommendationEntity> recommendationEntities = repository.findByProductId(productId);
        assertEquals(2,recommendationEntities.size());
    }

    @Test
    public void getRecommendation(){
        List<RecommendationEntity> entities = repository.findByProductId(savedEntity.getProductId());
        assertEquals(1,entities.size());
        assertEqualsRecommendation(savedEntity,entities.get(0));
    }

    @Test
    public void updateRecommendation(){
        savedEntity.setAuthor("Karthik");
        repository.save(savedEntity);
        RecommendationEntity foundEntity = repository.findById(savedEntity.getId()).get();
        assertEquals(1,foundEntity.getVersion());
        assertEquals("Karthik",foundEntity.getAuthor());

    }
}
