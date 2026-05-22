package com.pinger.messaging;

import java.util.ResourceBundle;

public class LabelsProvider extends PropertiesProvider {

    private static LabelsProvider instance;

    private LabelsProvider(ResourceBundle resources) {
        super(resources);
    }

    public static LabelsProvider getInstance() {
        if (instance == null) {
            instance = new LabelsProvider(ResourceBundle.getBundle("labels"));
        }
        return instance;
    }

}
