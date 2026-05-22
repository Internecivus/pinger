package com.pinger;

import com.pinger.messaging.LoggingProvider;
import com.pinger.service.PingerService;
import org.slf4j.event.Level;

public class Main {

    static {
        System.setOut(LoggingProvider.createLoggingProxy(System.out, Level.INFO));
        System.setErr(LoggingProvider.createLoggingProxy(System.err, Level.ERROR));
    }

    public static void main(String[] args) {
        init();
    }

    public static void init() {
        PingerService.checkSitesStatusAndNotify();
    }

}
