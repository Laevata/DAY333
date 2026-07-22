package com.fx.api.model;

import java.time.Instant;
import java.util.List;

public record IncomingBatch(long batchId, Instant generatedAt, List<IncomingRate> rates) {}

