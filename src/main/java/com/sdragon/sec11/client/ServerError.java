package com.sdragon.sec11.client;

public class ServerError extends RuntimeException {

    public ServerError() {
        super("Server error");
    }
}
