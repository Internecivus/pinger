package com.pinger.messaging;


import java.util.*;

public abstract class PropertiesProvider {

    private static final String DEFAULT_DELIMITER = ".";
    private String delimiter;
    private ResourceBundle resources;

    protected PropertiesProvider(String delimiter, ResourceBundle resources) {
        this.delimiter = delimiter;
        this.resources = resources;
    }

    protected PropertiesProvider(ResourceBundle resources) {
        this.resources = resources;
        this.delimiter = DEFAULT_DELIMITER;
    }

    public String getString(final String key) {
        try {
            return resources.getString(key);
        }
        catch (final MissingResourceException e) {
            return null;
        }
    }

    public String getString(final String... keyParts) {
        final StringBuilder fullKey = new StringBuilder();
        for (int i = 0; i < keyParts.length; i++) {
            final Object key = keyParts[i];

            fullKey.append(key);
            if (i < keyParts.length - 1) {
                fullKey.append(delimiter);
            }
        }
        return getString(fullKey.toString());
    }

    public <T> String getString(final Class<T> className, final String key) {
        return getString(className.getSimpleName() + delimiter + key);
    }

    public String[] getStrings(final String... keys) {
        final String[] labels = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            labels[i] = getString(keys[i]);
        }
        return labels;
    }

    public <T> String[] getStrings(final Class<T> className, final String... keys) {
        final String[] fullKeys = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            fullKeys[i] = className.getSimpleName() + keys[i];
        }
        return getStrings(fullKeys);
    }

    public Integer getInteger(final String key) {
        final String value = getString(key);
        return value != null ? Integer.valueOf(value) : null;
    }

    public Integer getInteger(final String key, final Integer defaultValue) {
        final Integer value = getInteger(key);
        return value != null ? value : defaultValue;
    }

    public Boolean getBoolean(final String key) {
        final String value = getString(key);
        return value != null ? Boolean.valueOf(getString(key)) : null;
    }

    public Boolean getBoolean(final String key, final Boolean defaultValue) {
        final Boolean value = getBoolean(key);
        return value != null ? value : defaultValue;
    }

    public String getMessage(final String key) {
        return getString("message" + delimiter + key);
    }

    public List<String> getKeysWithPrefix(final String prefix) {
        final List<String> keysWithPrefix = new ArrayList<>();

        final Enumeration<String> keys = resources.getKeys();
        while (keys.hasMoreElements()) {
            final String key = keys.nextElement();
            if (key.startsWith(prefix + delimiter)) {
                keysWithPrefix.add(key);
            }
        }
        return keysWithPrefix;
    }

    public String getNextKeyPart(final String key, final String previousPart) {
        final int prefixEnd = previousPart.length() - 1;
        final int nextKeyStart = prefixEnd + 2;
        if (nextKeyStart >= key.length()) {
            return null;
        }
        else {
            return key.substring(nextKeyStart).split("\\" + delimiter)[0];
        }
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public ResourceBundle getResources() {
        return resources;
    }

    public void setResources(ResourceBundle resources) {
        this.resources = resources;
    }
}
