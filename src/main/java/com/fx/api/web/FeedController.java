package com.fx.api.web;

import com.fx.api.model.IncomingBatch;
import com.fx.api.service.FeedService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    private final FeedService feed;

    public FeedController(FeedService feed) {
        this.feed = feed;
    }

    @PostMapping("/rates")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void receive(@RequestBody IncomingBatch batch) {
        feed.handle(batch);
    }
}

