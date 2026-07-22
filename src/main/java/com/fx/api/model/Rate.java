package com.fx.api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/** Row of Week-1's fx_rate table. Records: immutable, concise — the "newer features" payoff. */
public record Rate(int id, String baseCode, String quoteCode, double rate,
                   LocalDate rateDate, LocalDateTime capturedAt) {
    public String pair() { return baseCode + "/" + quoteCode; }
}
