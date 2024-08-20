package com.sdragon.tests;

import com.sdragon.common.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Objects;

/*
 *  "assertNext" is a method in StepVerifier
 *  assertNext = consumeNextWith
 *  We can also collect all the items and test.
 */
public class Lec05AssertNextTest {

    record Book(int id, String author, String title) {}

    private Flux<Book> getBook() {
        return Flux.range(1, 3)
                .map(i -> new Book(i, Util.faker().book().author(), Util.faker().book().title()));
    }

    @Test
    @DisplayName("assertNext Test")
    public void assertNextTest() {
        StepVerifier.create(getBook())
                .assertNext(book -> Assertions.assertEquals(1, book.id()))
                .thenConsumeWhile(book -> Objects.nonNull(book.title()))
                .thenConsumeWhile(book -> Objects.nonNull(book.author()))
                .verifyComplete();
    }

    @Test
    @DisplayName("Collect All Test - Mono")
    public void collectAllAndTest() {
        StepVerifier.create(getBook().collectList())
                .assertNext(list -> Assertions.assertEquals(3, list.size()))
                .verifyComplete();
    }
}
