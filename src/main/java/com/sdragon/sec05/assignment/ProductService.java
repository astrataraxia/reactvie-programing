package com.sdragon.sec05.assignment;

import com.sdragon.common.AbstractHttpClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * We have 4 product ids - 1, 2, 3, 4
 * Get the product name using product-service
 * Timeout 2 seconds
 *      - call fallback for timeout service to get the product name
 * call fallback for empty service to get the product name in case of empty
 * Let the client class abstract timeout/empty handling!
 *      - client getProductName(1)
 */

public class ProductService extends AbstractHttpClient {

    public Mono<String> getProductName(int productId) {
        var defaultPath = "/demo03/product/"+ productId;
        var emptyPath = "/demo03/empty-fallback/product/" + productId;
        var timeoutPath = "/demo03/timeout-fallback/product/" + productId;
        return getProductName(defaultPath)
                .timeout(Duration.ofMillis(2300), getProductName(timeoutPath))
                .switchIfEmpty(getProductName(emptyPath));
    }

    private Mono<String> getProductName(String path) {
        return this.httpClient.get()
                .uri(path)
                .responseContent()
                .asString()
                .next();
    }
}
