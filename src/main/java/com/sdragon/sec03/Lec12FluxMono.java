package com.sdragon.sec03;

import com.sdragon.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec12FluxMono {
    public static void main(String[] args) {
        fluxToMono();
        monoToFlux();
    }

    private static void fluxToMono() {
        var flux = Flux.range(1, 10);
        Mono.from(flux)
                .subscribe(Util.subscriber());
    }

    private static void monoToFlux() {
        var mono = getUsername(1);
        save(Flux.from(mono));
    }

    private static Mono<String> getUsername(int userId) {
        return switch (userId) {
            case 1 -> Mono.just("kim");
            case 2 -> Mono.empty(); // like null. react programing is not have null
            default -> Mono.error(new RuntimeException("Invalid user id: " + userId));
        };
    }

    private static void save(Flux<String> flux) {
        flux.subscribe(Util.subscriber());
    }

}
