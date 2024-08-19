package com.sdragon.sec12.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class SlackMember {

    private static final Logger log = LoggerFactory.getLogger(SlackMember.class);

    private final String name;
    private Consumer<String> messageConsumer;

    public SlackMember(String name) {
        this.name = name;
    }

    public void receive(String message) {
        log.info(message);
    }

    public void says(String message) {
        this.messageConsumer.accept(message);
    }

    public String getName() {
        return name;
    }

    public void setMessageConsumer(Consumer<String> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }
}
