package com.pinger.model;

import java.util.Date;

public abstract class SiteLink {

    protected final String url;
    protected final SiteType type;
    protected SiteStatus status;
    protected Double sResponseTime;
    protected Date timestamp;

    public SiteLink(String url, SiteType type) {
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public SiteType getType() {
        return type;
    }

    public SiteStatus getStatus() {
        return status;
    }

    public void setStatus(SiteStatus status) {
        this.status = status;
    }

    public Double getsResponseTime() {
        return sResponseTime;
    }

    public void setsResponseTime(Double sResponseTime) {
        this.sResponseTime = sResponseTime;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
