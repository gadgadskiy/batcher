package com.batcher.batcher;

import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Data
@Builder(toBuilder = true)
public class BatchRequest {
    private Integer batchId;
    CompletableFuture<Integer> future;

    public void execute() {
        fakeRequestProcessing();
        future.complete(batchId);
    }

    @SneakyThrows
    private void fakeRequestProcessing() {
        batchId *= 100;
        Thread.sleep(new Random().nextInt(1, 5));
    }
}
