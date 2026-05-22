package com.pinger.model;


import java.util.Date;

public class PingResults {

    private final boolean successful;
    private final Double sResponseTime;
    private final Date timestamp;

    public PingResults(boolean successful, Double sResponseTime, Date timestamp) {
        this.successful = successful;
        this.sResponseTime = sResponseTime;
        this.timestamp = timestamp;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public Double getsResponseTime() {
        return sResponseTime;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
