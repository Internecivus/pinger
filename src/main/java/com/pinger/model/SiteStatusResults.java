package com.pinger.model;

import com.pinger.messaging.LabelsProvider;
import com.pinger.table.Table;
import com.pinger.template.Template;
import com.pinger.template.TemplateService;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.*;

public class SiteStatusResults extends Table implements SiteLinkRepository {

    private List<Site> sites;
    private Date timestamp;

    public List<Site> getSites() {
        return sites;
    }

    public void setSites(List<Site> sites) {
        this.sites = sites;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public List<SiteLink> getAllLinks() {
        final List<SiteLink> links = new ArrayList<>();
        for (final Site site : sites) {
            links.addAll(site.getAllLinks());
        }
        return links;
    }

    @Override
    public List<SiteLink> getAllDownLinks() {
        final List<SiteLink> downLinks = new ArrayList<>();
        for (final Site site : sites) {
            downLinks.addAll(site.getAllDownLinks());
        }
        return downLinks;
    }

    @Override
    public List<SiteLink> getAllOnlineLinks() {
        final List<SiteLink> onlineLinks = new ArrayList<>();
        for (final Site site : sites) {
            onlineLinks.addAll(site.getAllOnlineLinks());
        }
        return onlineLinks;
    }

    @Override
    public boolean isAnyLinkDown() {
        boolean anyIsDown = false;
        for (final Site site : sites) {
            if (site.isAnyLinkDown()) {
                anyIsDown = true;
                break;
            }
        }
        return anyIsDown;
    }

    @Override
    public String convertToTextTable() {
        final StringBuilder builder = new StringBuilder();

        builder.append(System.lineSeparator());
        builder.append(System.lineSeparator());
        builder.append(MessageFormat.format(LabelsProvider.getInstance().getString("site_status.results_summary"),
            getAllOnlineLinks().size(),
            getAllLinks().size()));
        builder.append(System.lineSeparator());
        builder.append(System.lineSeparator());
        builder.append(LabelsProvider.getInstance().getString("site_status.results_header"));
        builder.append(System.lineSeparator());

        builder.append(super.convertToTextTable());

        return builder.toString();
    }

    @Override
    public String convertToHtmlTable() {
        if (sites == null || sites.isEmpty()) return null;

        final Map<String, Object> model = new HashMap<>();
        model.put("sites", sites);

        return TemplateService.process(Template.SITE_STATUS_TABLE, model);
    }

    @Override
    public void setData() {
        setHeaders(Arrays.asList(
                LabelsProvider.getInstance().getString(Site.class, "tag"),
                LabelsProvider.getInstance().getString(SiteLink.class, "type"),
                LabelsProvider.getInstance().getString(SiteLink.class, "url"),
                LabelsProvider.getInstance().getString(SiteLink.class, "status"),
                LabelsProvider.getInstance().getString(SiteLink.class, "sResponseTime")));

        for (final Site site : sites) {
            for (final SiteLink siteLink : site.getAllLinks()) {
                addRow(Arrays.asList(
                        site.getTag(),
                        siteLink.getType(),
                        siteLink.getUrl(),
                        siteLink.getStatus(),
                        siteLink.getsResponseTime() != null ? new DecimalFormat().format(siteLink.getsResponseTime()) : null));
            }
        }
    }

}
