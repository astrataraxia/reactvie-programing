package com.sdragon.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Lec03FluxTest {

    private Flux<Integer> getItems() {
        return Flux.just(1, 2, 3, 4, 5)
                .log();
    }

    @Test
    @DisplayName("Get Item only one")
    public void fluxTest1() {
        StepVerifier.create(getItems(), 1)
                .expectNext(1)
                .thenCancel()
                .verify();
    }

    @Test
    @DisplayName("Get Item" )
    public void fluxTest2() {
        StepVerifier.create(getItems())
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();
    }


}
