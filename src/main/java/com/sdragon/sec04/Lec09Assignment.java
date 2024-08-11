package com.sdragon.sec04;

import com.sdragon.common.Util;
import com.sdragon.sec04.assignment.FileReaderServiceImpl;
import reactor.core.publisher.Flux;

import java.nio.file.Path;

public class Lec09Assignment {

    private static final Path PATH = Path.of("src/main/resources/sec04");
    public static void main(String[] args) {
        FileReaderServiceImpl fileReaderService = new FileReaderServiceImpl();
        fileReaderService.read(PATH.resolve("file.txt"))
                .take(6)
                .subscribe(Util.subscriber());
    }

}
