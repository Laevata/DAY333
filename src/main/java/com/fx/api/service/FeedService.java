package com.fx.api.service;

import com.fx.api.model.IncomingBatch;
import com.fx.api.model.IncomingRate;
import com.fx.api.repo.RateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FeedService {

    private static final Logger log = LoggerFactory.getLogger(FeedService.class);

    private final RateRepository rates;
    private final OrchestratorClient orchestrator;
    private final AcceptingState accepting;

    public FeedService(RateRepository rates, OrchestratorClient orchestrator, AcceptingState accepting) {
        this.rates = rates;
        this.orchestrator = orchestrator;
        this.accepting = accepting;
    }

    public void handle(IncomingBatch batch) {
        if (batch == null || batch.rates() == null) {
            return;
        }

        if (!accepting.isAccepting()) {
            log.info("Declined batch {} (accepting OFF)", batch.batchId());
            orchestrator.ack(batch.batchId(), "DECLINED");
            return;
        }

        int stored = 0;
        for (IncomingRate tick : batch.rates()) {
            if (tick == null || tick.base() == null || tick.quote() == null) {
                continue;
            }
            rates.insert(tick.base().toUpperCase(), tick.quote().toUpperCase(), tick.rate());
            stored++;
        }

        log.info("Stored {} rates from batch {}", stored, batch.batchId());
        orchestrator.ack(batch.batchId(), "ACCEPTED");
    }
}

