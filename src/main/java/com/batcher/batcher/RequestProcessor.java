package com.batcher.batcher;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RequestProcessor {
    private static final int TIMEOUT = 100;
    private static final int BATCH_SIZE = 100;

    private final BlockingQueue<BatchRequest> requestQueue = new LinkedBlockingQueue<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public CompletableFuture<Integer> addRequest(BatchRequest request) {
        var future = new CompletableFuture<Integer>();
        requestQueue.add(request.toBuilder().future(future).build());
        return future;
    }

    public void init() {
        scheduler.scheduleAtFixedRate(this::processBatch, 0, TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private void processBatch() {
        try {
            var batch = new ArrayList<BatchRequest>(BATCH_SIZE);
            requestQueue.drainTo(batch, BATCH_SIZE);
            if (!batch.isEmpty()) {
                System.out.printf("Process %s items, %s remained in the queue%n", batch.size(), requestQueue.size());
                batch.forEach(BatchRequest::execute);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
