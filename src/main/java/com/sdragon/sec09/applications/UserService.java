package com.sdragon.sec09.applications;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

import static reactor.core.publisher.Mono.fromSupplier;

/*
 *  Just for demo
 *  Imagine user-service, as an application, has 2 endpoints.
 *  This is a client class which represents to call those 2 endpoints (10 requests).
 */

public class UserService {

    public static final Map<String, Integer> userTable = Map.of(
            "sam", 1,
            "mike", 2,
            "jake", 3);


    public static Flux<User> getAllUsers() {
        return Flux.fromIterable(userTable.entrySet())
                .map(entry -> new User(entry.getValue(), entry.getKey()));
    }

    public static Mono<Integer> getUserId(String username) {
        return fromSupplier(() -> userTable.get(username));
//        error handling
//        return justOrEmpty(userTable.get(username))
//                .switchIfEmpty(error(new IllegalArgumentException("User not found: " + username)));
    }
}
