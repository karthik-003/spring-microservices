package com.karvis.product.api;

import com.karvis.api.product.Product;
import com.karvis.product.repo.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.core.publisher.Mono.just;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.data.mongodb.port:0"})
public class ProductApiTest {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private WebTestClient client;

    @BeforeEach
    public void setupDb(){
        repo.deleteAll();
    }

    private WebTestClient.BodyContentSpec postAndVerifyProduct(int productId, HttpStatus expectedStatus){
        Product product = new Product(productId,"Name "+productId,10,"SA");
        WebTestClient.BodyContentSpec response =
         client.post()
                .uri("/product")
                .body(just(product),Product.class)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
        System.out.println("Post & verify output: "+new String(response.returnResult().getResponseBody()));
        return response;
    }

    private WebTestClient.BodyContentSpec getAndVerifyProduct(int productId, HttpStatus expectedStatus) {
        return getAndVerifyProduct("/" + productId, expectedStatus);
    }

    private WebTestClient.BodyContentSpec getAndVerifyProduct(String productIdPath, HttpStatus expectedStatus) {
        return client.get()
                .uri("/product" + productIdPath)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }

    @Test
    public void getProductById(){
        int productId = 1;
        WebTestClient.BodyContentSpec bodyResponse = postAndVerifyProduct(productId,OK);
        System.out.println(new String(bodyResponse.returnResult().getResponseBody()));
        //System.out.println(bodyResponse.jsonPath("$.productId").toString());
        assertTrue(repo.findByProductId(productId).isPresent());
        getAndVerifyProduct(productId,OK).jsonPath("$.productId").isEqualTo(productId);

    }


}
