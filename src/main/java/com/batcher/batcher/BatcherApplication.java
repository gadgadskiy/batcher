package com.batcher.batcher;

import lombok.SneakyThrows;

import static com.batcher.batcher.RequestEmulator.EMULATED_REQUESTS_COUNT;

public class BatcherApplication {

    private final RequestProcessor requestProcessor = new RequestProcessor();
    private final RequestEmulator requestEmulator = new RequestEmulator(requestProcessor);

    public static void main(String[] args) {
        new BatcherApplication();
    }

    @SneakyThrows
    public BatcherApplication() {
        new Thread(logger()).start();
        requestProcessor.init();
        requestEmulator.emulate();
    }

    private Runnable logger() {
        return () -> {
            while (true) {
                var size = requestEmulator.getProcessedCount();
                System.out.println("Processed: " + size);
                if (size >= EMULATED_REQUESTS_COUNT) {
                    System.out.println("Everything completed");
                    System.exit(0);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

}
