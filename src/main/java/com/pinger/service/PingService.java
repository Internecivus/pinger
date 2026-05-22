package com.pinger.service;

import com.pinger.messaging.SettingsProvider;
import com.pinger.model.PingResults;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PingService {

    final static int PORT = 80;
    final static int SLEEP_TIME_BETWEEN_RETRIES_S = 3;
    final static int TIMEOUT = SettingsProvider.getInstance().getInteger("ping.timeout", 15);
    final static int RETRY_TIMES = SettingsProvider.getInstance().getInteger("ping.retry_times", 5);

    public static PingResults checkUrl(final String url) {
        final Date timestamp = new Date();
        final double nsPingStart = System.nanoTime();
        final boolean isSuccess = urlIsPingable(url);
        final double nsPingEnd = System.nanoTime();
        return new PingResults(isSuccess, (nsPingEnd - nsPingStart) / 1_000_000_000, timestamp);
    }

    private static boolean urlIsPingable(final String url) {
        boolean isSuccess = false;
        for (int retryNo = 1; retryNo <= RETRY_TIMES; retryNo++) {
            try (Socket socket = new Socket()) {
                if (retryNo != 1) {
                    TimeUnit.SECONDS.sleep(SLEEP_TIME_BETWEEN_RETRIES_S);
                }
                socket.connect(new InetSocketAddress(new URL(url).getHost(), PORT), TIMEOUT);
                isSuccess = true;
                break;
            }
            catch (IOException | InterruptedException e) {}
        }
        return isSuccess;
    }

}
