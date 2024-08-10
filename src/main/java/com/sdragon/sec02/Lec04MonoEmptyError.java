package com.sdragon.sec02;

import reactor.core.publisher.Mono;

public class Lec04MonoEmptyError {

    public static void main(String[] args) {
        getUsername(3)
                .subscribe(s -> System.out.println(s),
                        err -> {}
                );
    }

    private static Mono<String> getUsername(int userId) {
        return switch (userId) {
            case 1 -> Mono.just("kim");
            case 2 -> Mono.empty(); // like null. react programing is not have null
            default -> Mono.error(new RuntimeException("Invalid user id: " + userId));
        };
    }
}
