package com.sdragon.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec02EmptyErrorTest {

    Mono<String> getUsername(int userId) {
        return switch (userId) {
            case 1 -> Mono.just("sam");
            case 2 -> Mono.empty();
            default -> Mono.error(new RuntimeException("invalid user id"));
        };
    }

    @Test
    @DisplayName("Get the value right through the given value")
    public void userTest() {
        StepVerifier.create(getUsername(1))
                .expectNext("sam")
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Empty Test")
    public void emptyTest() {
        StepVerifier.create(getUsername(2))
                .verifyComplete();
//                .expectNext()
//                .expectComplete()
//                .verify();
    }

    @Test
    @DisplayName("Error test1 - any error signal pass")
    public void errorTest1() {
        StepVerifier.create(getUsername(3))
                .verifyError();
//                .expectError()
//                .verify();
    }

    @Test
    @DisplayName("Error test2 - collect error test")
    public void errorTest2() {
        StepVerifier.create(getUsername(3))
                .verifyError(RuntimeException.class);
//                .expectError(RuntimeException.class)
//                .verify();
    }

    @Test
    @DisplayName("Error test3 - expect error message test")
    public void errorTest3() {
        StepVerifier.create(getUsername(3))
                .verifyErrorMessage("invalid user id");
//                .expectErrorMessage("invalid user id")
//                .verify();
    }

    @Test
    @DisplayName("Error test4 - more control over testing")
    public void errorTest4() {
        StepVerifier.create(getUsername(3))
                .consumeErrorWith(ex -> {
                    Assertions.assertEquals(RuntimeException.class, ex.getClass());
                    Assertions.assertEquals("invalid user id", ex.getMessage());
                })
                .verify();
    }

}