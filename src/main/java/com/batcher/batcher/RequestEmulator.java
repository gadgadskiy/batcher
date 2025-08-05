package com.batcher.batcher;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.Random;

@RequiredArgsConstructor
public class RequestEmulator {
    private final RequestProcessor requestProcessor;

    @SneakyThrows
    public void emulate() {
        for (var i = 0; i < 10_000; i++) {
            requestProcessor.addRequest(BatchRequest.builder().batchId(i).build());
            Thread.sleep(getRandomDelay());
        }
    }

    private long getRandomDelay() {
        var delay = Math.random() < 0.001 ? 100 : 0;
        return delay + new Random().nextLong(2);
    }
}
