package com.fx.api.web;

import com.fx.api.model.AcceptingStateResponse;
import com.fx.api.model.AcceptingUpdateRequest;
import com.fx.api.service.AcceptingState;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AcceptingState state;

    public AdminController(AcceptingState state) {
        this.state = state;
    }

    @GetMapping("/accepting")
    public AcceptingStateResponse getAccepting() {
        return new AcceptingStateResponse(state.isAccepting());
    }

    @PostMapping("/accepting")
    public AcceptingStateResponse setAccepting(@RequestBody AcceptingUpdateRequest req) {
        return new AcceptingStateResponse(state.set(req.accepting()));
    }
}

