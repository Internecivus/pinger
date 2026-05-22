package com.pinger.model;


import java.util.ArrayList;
import java.util.List;

public class Site implements SiteLinkRepository {

    private String tag;
    private final String name;
    private final String description;

    private final SiteProdLink prodLink;
    private final SiteDevLink devLink;
    private final SiteTestLink testLink;

    public Site(String tag, String name, String description, SiteProdLink prodLink, SiteDevLink devLink, SiteTestLink testLink) {
        this.tag = tag;
        this.name = name;
        this.description = description;
        this.prodLink = prodLink;
        this.devLink = devLink;
        this.testLink = testLink;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public SiteProdLink getProdLink() {
        return prodLink;
    }

    public SiteDevLink getDevLink() {
        return devLink;
    }

    public SiteTestLink getTestLink() {
        return testLink;
    }

    @Override
    public List<SiteLink> getAllLinks() {
        final List<SiteLink> siteLinks = new ArrayList<>();
        if (prodLink != null) siteLinks.add(prodLink);
        if (devLink != null) siteLinks.add(devLink);
        if (testLink != null) siteLinks.add(testLink);
        return siteLinks;
    }

    @Override
    public boolean isAnyLinkDown() {
        boolean anyIsDown = false;
        for (final SiteLink link : getAllLinks()) {
            if (SiteStatus.DOWN.equals(link.getStatus())) {
                anyIsDown = true;
                break;
            }
        }
        return anyIsDown;
    }

    @Override
    public List<SiteLink> getAllOnlineLinks() {
        final List<SiteLink> onlineLinks = new ArrayList<>();
        for (final SiteLink link : getAllLinks()) {
            if (SiteStatus.ONLINE.equals(link.getStatus())) {
                onlineLinks.add(link);
            }
        }
        return onlineLinks;
    }

    @Override
    public List<SiteLink> getAllDownLinks() {
        final List<SiteLink> downLinks = new ArrayList<>();
        for (final SiteLink link : getAllLinks()) {
            if (SiteStatus.DOWN.equals(link.getStatus())) {
                downLinks.add(link);
            }
        }
        return downLinks;
    }
}
