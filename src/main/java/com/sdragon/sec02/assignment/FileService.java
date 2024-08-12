package com.sdragon.sec02.assignment;

import reactor.core.publisher.Mono;

public interface FileService {
    Mono<String> read(String filename);
    Mono<Void> write(String filename, String content);
    Mono<Void> delete(String filename);
}

/*
 * Assumptions
 *  - Assume files are very small in size (no Out Of Memory)
 *  - File are present under src/main/resources/sec02
 *
 * Expectations
 *  - File service methods should do the work only when subscribers subscribe to that
 *  - Communicate the error to the subscriber in case of issues
 *  - Just use whatever we have learnt so far + any utility classes. No need for any special library. (java.nio.file.*)
 *
 * Note
 *  - The purpose of the assignment is to "try"!
 *  - You do Not have to do exactly as I do!
 */