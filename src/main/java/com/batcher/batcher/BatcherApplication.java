package com.batcher.batcher;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
                var size = requestProcessor.getProcessed().size();
                if (size >= 10000) {
                    System.out.println("Everything completed");
                    System.exit(0);
                }
                System.out.println("Processed: " + size);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

}
