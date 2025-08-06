package com.batcher.batcher;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class RequestEmulator {
    public final static int EMULATED_REQUESTS_COUNT = 10_000;

    private final RequestProcessor requestProcessor;
    private final Set<CompletableFuture<Integer>> responses = ConcurrentHashMap.newKeySet();

    @SneakyThrows
    public void emulate() {
        for (var i = 0; i < EMULATED_REQUESTS_COUNT; i++) {
            responses.add(requestProcessor.addRequest(BatchRequest.builder().batchId(i).build()));
            Thread.sleep(getRandomDelay());
        }
    }

    public long getProcessedCount() {
        return responses.stream().filter(CompletableFuture::isDone).count();
    }

    private long getRandomDelay() {
        var delay = Math.random() < 0.001 ? 100 : 0;
        return delay + new Random().nextLong(2);
    }
}
