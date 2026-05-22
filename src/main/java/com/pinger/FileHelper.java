package com.pinger;

import com.pinger.messaging.SettingsProvider;

import java.io.File;
import java.net.URISyntaxException;

public class FileHelper {

    public static File loadFromJarFolder(final String fileName) {
        try {
            final File jar = new File(SettingsProvider.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            return new File(jar.getParentFile().toString() + File.separator + fileName);
        }
        catch (final URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
