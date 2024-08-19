package com.sdragon.sec12.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Predicate;

public class SlackRoom {

    private static final Logger log = LoggerFactory.getLogger(SlackRoom.class);

    private final String name;
    private final Sinks.Many<SlackMessage> sink;
    private final Flux<SlackMessage> flux;

    public SlackRoom(String name) {
        this.name = name;
        this.sink = Sinks.many().replay().all();
        this.flux = sink.asFlux();
    }

    public void addMember(SlackMember slackMember) {
        log.info("{} joined the room {}", slackMember.getName(), name);
        this.subscribeToRoomMessages(slackMember);
        slackMember.setMessageConsumer(msg -> post(slackMember.getName(), msg));
    }

    private void subscribeToRoomMessages(SlackMember slackMember) {
        this.flux.filter(isNotSender(slackMember))
                .map(sm -> sm.formatForDelivery(slackMember.getName()))
                .subscribe(slackMember::receive);
    }

    private Predicate<SlackMessage> isNotSender(SlackMember slackMember) {
        return sm -> !slackMember.getName().equals(sm.sender());
    }

    private void post(String sender, String message) {
        this.sink.tryEmitNext(new SlackMessage(sender, message));
    }
}
