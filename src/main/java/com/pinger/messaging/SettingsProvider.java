package com.pinger.messaging;

import com.pinger.FileHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class SettingsProvider extends PropertiesProvider {

    private static SettingsProvider instance;

    private SettingsProvider(ResourceBundle resources) {
        super(resources);
    }

    public static SettingsProvider getInstance() {
        if (instance == null) {
            ResourceBundle bundle;
            try {
                bundle = new PropertyResourceBundle(new FileInputStream(FileHelper.loadFromJarFolder("settings.properties")));
            }
            catch (final IOException e) {
                bundle = ResourceBundle.getBundle("settings");
            }
            instance = new SettingsProvider(bundle);
        }
        return instance;
    }

}
