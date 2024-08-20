package com.sdragon.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

public class Lec10TimeoutTest {


    private Flux<Integer> getItems() {
        return Flux.range(1, 5)
                .delayElements(Duration.ofMillis(200));
    }

    @Test
    @DisplayName("Timeout Success - All items emitted within timeout")
    public void timeoutSuccessTest() {
        StepVerifier.create(getItems())
                .expectNext(1, 2, 3, 4, 5)
                .expectComplete()
                .verify(Duration.ofSeconds(2));
    }

    @Test
    @DisplayName("Timeout Failure - Not all items emitted within timeout")
    public void timeoutFailureTest() {
        StepVerifier.create(getItems())
                .expectNext(1, 2)
                .expectComplete()
                .verify(Duration.ofMillis(500));
    }

}

