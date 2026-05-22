package com.pinger.model;

import java.util.List;

public interface SiteLinkRepository {

    boolean isAnyLinkDown();

    List<SiteLink> getAllLinks();

    List<SiteLink> getAllDownLinks();

    List<SiteLink> getAllOnlineLinks();

}
