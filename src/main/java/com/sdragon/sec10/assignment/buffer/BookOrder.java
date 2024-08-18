package com.sdragon.sec10.assignment.buffer;

import com.sdragon.common.Util;

public record BookOrder(String genre, String title, Integer price) {
    // faker has "book()" to create this object with random values

    public static BookOrder create() {
        var book = Util.faker().book();
        return new BookOrder(
                book.genre(),
                book.title(),
                Util.faker().random().nextInt(10, 100)
        );
    }
}
