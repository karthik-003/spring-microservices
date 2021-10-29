package com.karvis.recommendation.api;

import com.karvis.api.recommendation.Recommendation;
import com.karvis.recommendation.entity.RecommendationEntity;
import com.karvis.recommendation.repo.RecommendationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,properties = {"spring.data.mongodb.port: 0 "})
public class RecommendationApiTest {

    @Autowired
    private WebTestClient client;
    @Autowired
    RecommendationRepository repository;

    @BeforeEach
    public void setupDb(){
        repository.deleteAll();
    }

    public WebTestClient.BodyContentSpec postAndVerify(Recommendation api, HttpStatus expectedStatus){
        return client.post()
                .uri("/recommendation")
                .body(Mono.just(api),Recommendation.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody();
    }
    public WebTestClient.BodyContentSpec getAndVerify(int productId, HttpStatus expectedStatus){
        return client.get()
                .uri("/recommendation?productId="+productId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody();
    }

    @Test
    public void createRecommendations(){
        int productId = 1;


        postAndVerify(new Recommendation(productId,1,"Karthik",3,"Recommended produt","SA"),HttpStatus.OK);
        postAndVerify(new Recommendation(productId,2,"Karthik",3,"Recommended produt 2","SA"),HttpStatus.OK);
        postAndVerify(new Recommendation(productId,3,"Karthik",3,"Recommended produt 3","SA"),HttpStatus.OK);

        assertEquals(3,repository.count());
    }

    @Test
    public void getRecommendationsForProduct(){
        int productId = 1;
        postAndVerify(new Recommendation(productId,1,"Karthik",3,"Recommended produt","SA"),HttpStatus.OK);
        postAndVerify(new Recommendation(productId,2,"Karthik",3,"Recommended produt 2","SA"),HttpStatus.OK);
        getAndVerify(1,HttpStatus.OK)
                .jsonPath("$.length()").isEqualTo(2);
    }
}
