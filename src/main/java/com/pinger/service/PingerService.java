package com.pinger.service;

import com.pinger.messaging.EmailService;
import com.pinger.messaging.SettingsProvider;
import com.pinger.model.*;

import java.util.Date;
import java.util.List;

public class PingerService {

    final static Boolean NOTIFY_DOWN_ONLY = SettingsProvider.getInstance().getBoolean("send_email_down_only", false);

    public static void checkSitesStatusAndNotify() {
        final List<Site> sites = SiteService.getAll();
        final SiteStatusResults statusResults = new SiteStatusResults();
        statusResults.setTimestamp(new Date());

        for (final Site site : sites) {
            for (final SiteLink link : site.getAllLinks()) {
                checkSiteLinkStatus(link);
            }
        }
        statusResults.setSites(sites);

        if (statusResults.isAnyLinkDown() || !NOTIFY_DOWN_ONLY) {
            EmailService.notifySitesStatus(statusResults);
        }
        System.out.println(statusResults.convertToTextTable());
    }

    private static void checkSiteLinkStatus(final SiteLink siteLink) {
        if (siteLink == null) return;

        final PingResults pingResults = PingService.checkUrl(siteLink.getUrl());
        final SiteStatus siteStatus = pingResults.isSuccessful() ? SiteStatus.ONLINE : SiteStatus.DOWN;
        siteLink.setStatus(siteStatus);

        if (siteStatus.equals(SiteStatus.ONLINE)) {
            siteLink.setsResponseTime(pingResults.getsResponseTime());
            siteLink.setTimestamp(pingResults.getTimestamp());
        }
    }
}
