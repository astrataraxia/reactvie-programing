package com.sdragon.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileServiceImpl implements FileService {

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
    private static final Path PATH = Path.of("src/main/resources/sec02/");

    @Override
    public Mono<String> read(String filename) {
        return Mono.fromCallable(() -> Files.readString(PATH.resolve(filename)));
    }

    @Override
    public Mono<Void> write(String filename, String content) {
        return Mono.fromRunnable(() -> writeFile(filename, content));
    }

    @Override
    public Mono<Void> delete(String filename) {
        return Mono.fromRunnable(() -> deleteFile(filename));
    }

    private void writeFile(String filename, String content) {
        try {
            Files.writeString(PATH.resolve(filename), content);
            log.info("created {}", filename);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + filename, e);
        }
    }

    private void deleteFile(String filename) {
        try {
            Files.delete(PATH.resolve(filename));
            log.info("Deleting file {}", filename);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting file: " + filename, e);
        }
    }
}
