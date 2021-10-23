package com.karvis.review.api;


import com.karvis.api.review.Review;
import com.karvis.review.entity.ReviewEntity;
import com.karvis.review.repo.ReviewRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,properties = {
        "spring.datasource.url=jdbc:h2:mem:review-db"
})
public class ReviewApiTest {

    @Autowired
    private WebTestClient client;
    @Autowired
    private ReviewRepository repository;

    @BeforeEach
    public void setupDb(){
        repository.deleteAll();

    }

    @Test
    public void getReviewsByProductId(){
        int productId = 1;
        assertEquals(0,repository.findByProductId(productId).size());

        postAndVerifyReview(1,1,HttpStatus.OK);
        postAndVerifyReview(1,2,HttpStatus.OK);
        postAndVerifyReview(2,1,HttpStatus.OK);

        assertEquals(2,repository.findByProductId(1).size());
        getAndVerifyReviewsByProductId(productId,HttpStatus.OK);

    }

    @Test
    public void createDuplicateReview(){
        int productId = 1;
        assertEquals(0,repository.findByProductId(productId).size());

        postAndVerifyReview(1,1,HttpStatus.OK);
        postAndVerifyReview(1,1,HttpStatus.INTERNAL_SERVER_ERROR);

    }


    public WebTestClient.BodyContentSpec postAndVerifyReview(int productId, int reviewId, HttpStatus expectedStatus){
        Review review = new Review(productId,reviewId,"Karthik","Super product","Spure product in the given price range","SA");
        return client.post()
                .uri("/review")
                .body(Mono.just(review),Review.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody();
    }

    public WebTestClient.BodyContentSpec getAndVerifyReviewsByProductId(int productId,HttpStatus expectedStatus){
        return client.get()
                .uri("/review?productId="+productId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();
    }
}
