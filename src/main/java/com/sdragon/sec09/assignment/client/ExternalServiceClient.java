package com.sdragon.sec09.assignment.client;

import com.sdragon.common.AbstractHttpClient;
import com.sdragon.sec09.assignment.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class ExternalServiceClient extends AbstractHttpClient {

    private static final Logger log = LoggerFactory.getLogger(ExternalServiceClient.class);

    public Mono<Product> getProduct(int productId) {
        return Mono.zip(
                        getProductName(productId),
                        getReview(productId),
                        getPrice(productId))
                .map(o -> new Product(o.getT1(), o.getT2(), o.getT3()));
    }

    /*
     * Price Service
     * /demo05/price/{id}
     * - Gives the price for product ids 1 - 10. Takes 1 second to respond.
     */
    private Mono<String> getPrice(int productId) {
        return get("/demo05/price/" + productId);
    }

    /*
     * Product Name Service
     * - /demo05/product/{id}
     * - Gives the product name for product ids 1 - 10. Takes 1 second to respond.
     */
    private Mono<String> getProductName(int productId) {
        return get("/demo05/product/" + productId);
    }

    /*
     * Review Service
     * /demo05/review/{id}
     * - Gives the review for product ids 1 - 10. Takes 1 second to respond.
     */
    private Mono<String> getReview(int productId) {
        return get("/demo05/review/" + productId);
    }

    private Mono<String> get(String path) {
        return this.httpClient.get()
                .uri(path)
                .responseContent()
                .asString()
                .next();
    }
}
