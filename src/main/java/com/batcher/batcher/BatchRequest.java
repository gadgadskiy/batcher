package com.batcher.batcher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BatchRequest {
    private Integer batchId;
}
