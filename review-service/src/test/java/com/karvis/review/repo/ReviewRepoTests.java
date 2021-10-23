package com.karvis.review.repo;

import com.karvis.review.entity.ReviewEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
public class ReviewRepoTests {

    @Autowired
    ReviewRepository repository;

    ReviewEntity savedEntity;


    @BeforeEach
    public void setupDb(){
        repository.deleteAll();

        ReviewEntity _entity = new ReviewEntity(1,2, "Karvis","Good Product","Product is good.");
        savedEntity = repository.save(_entity);


    }

    @Test
    public void getReviewByProductId(){
        int productId = 1;
        List<ReviewEntity> reviews = repository.findByProductId(productId);
        assertTrue(!reviews.isEmpty());
        assertEqualsReview(savedEntity,reviews.get(0));

    }
    @Test
    public void createReview(){
        ReviewEntity _newReview = new ReviewEntity(1,3, "Karvis","Good Product","Product is good.");
        ReviewEntity _createdReview = repository.save(_newReview);
        assertEqualsReview(_newReview,_createdReview);
    }
    @Test
    public void createReview_duplicate(){
        int productId = 1;
        ReviewEntity duplicateReview
                = new ReviewEntity(1,2, "Karvis","Good Product","Product is good.");
        assertThrows(DataIntegrityViolationException.class,()->{
            ReviewEntity _e = repository.save(duplicateReview);
            repository.flush(); // Without flush the data will not go to 'Actual' DB.
        });
    }
    private void assertEqualsReview(ReviewEntity expectedEntity, ReviewEntity actualEntity) {
        assertEquals(expectedEntity.getId(),        actualEntity.getId());
        assertEquals(expectedEntity.getVersion(),   actualEntity.getVersion());
        assertEquals(expectedEntity.getProductId(), actualEntity.getProductId());
        assertEquals(expectedEntity.getReviewId(),  actualEntity.getReviewId());
        assertEquals(expectedEntity.getAuthor(),    actualEntity.getAuthor());
        assertEquals(expectedEntity.getSubject(),   actualEntity.getSubject());
        assertEquals(expectedEntity.getContent(),   actualEntity.getContent());
    }

}
