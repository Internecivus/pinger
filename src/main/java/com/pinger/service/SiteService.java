package com.pinger.service;

import com.pinger.messaging.SettingsProvider;
import com.pinger.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SiteService {

    public static List<Site> getAll() {
        final List<Site> sites = new ArrayList<>();
        final List<String> siteKeys = SettingsProvider.getInstance().getKeysWithPrefix("site");
        final Set<String> siteTags = new HashSet<>();
        for (final String siteKey : siteKeys) {
            siteTags.add(SettingsProvider.getInstance().getNextKeyPart(siteKey, "site"));
        }

        for (final String siteTag : siteTags) {
            final String siteName = SettingsProvider.getInstance().getString("site", siteTag, "name");
            final String siteDescription = SettingsProvider.getInstance().getString("site", siteTag, "description");

            final String prodLinkUrl = SettingsProvider.getInstance().getString("site", siteTag, SiteType.PROD.name().toLowerCase(), "url");
            final String devLinkUrl = SettingsProvider.getInstance().getString("site", siteTag, SiteType.DEV.name().toLowerCase(), "url");
            final String testLinkUrl = SettingsProvider.getInstance().getString("site", siteTag, SiteType.TEST.name().toLowerCase(), "url");

            sites.add(new Site(siteTag.toUpperCase(), siteName, siteDescription,
                prodLinkUrl != null ? new SiteProdLink(prodLinkUrl) : null,
                devLinkUrl != null ? new SiteDevLink(devLinkUrl) : null,
                testLinkUrl != null ? new SiteTestLink(testLinkUrl) : null));
        }

        return sites;
    }

}
