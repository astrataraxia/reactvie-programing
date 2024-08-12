package com.sdragon.sec02;

import com.sdragon.sec02.assignment.FileServiceImpl;
import com.sdragon.common.Util;

public class Lec12Assignment {

    public static void main(String[] args) {
        FileServiceImpl fileService = new FileServiceImpl();

        fileService.write("file.txt", "This is a test file")
                .subscribe(Util.subscriber());

        fileService.read("file.txt")
                .subscribe(Util.subscriber());

        fileService.delete("file.txt")
                .subscribe(Util.subscriber());
    }
}
