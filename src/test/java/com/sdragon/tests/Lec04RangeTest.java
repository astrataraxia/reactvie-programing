package com.sdragon.tests;

import com.sdragon.common.Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Lec04RangeTest {

    private Flux<Integer> getItems() {
        return Flux.range(1, 50);
    }

    private Flux<Integer> getRandomItem() {
        return Flux.range(1, 50)
                .map(i -> Util.faker().random().nextInt(1, 100));
    }

    @Test
    @DisplayName("How to Test big range")
    public void rangeTest1() {
        StepVerifier.create(getItems())
                .expectNext(1, 2, 3)
                .expectNextCount(47L)
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify sequence of items with specific values and counts")
    public void rangeTest2() {
        StepVerifier.create(getItems())
                .expectNext(1, 2, 3)
                .expectNextCount(22)
                .expectNext(26,27,28)
                .expectNextCount(22)
                .verifyComplete();
    }

    @Test
    @DisplayName("Not sequential item test")
    public void rangeTest3() {
        StepVerifier.create(getRandomItem())
                .expectNextMatches(i -> i > 0 && i  < 101)
                .expectNextCount(49L)
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getRandomItem() produces values within range 1-100")
    public void rangeTest4() {
        StepVerifier.create(getRandomItem())
                .thenConsumeWhile(i -> i > 0 && i  < 101)
                .verifyComplete();
    }
}
