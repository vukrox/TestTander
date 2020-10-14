package ru.vukrox.periphery.configuration;

import ru.vukrox.periphery.misc.FromResourcesReader;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * "AppConfigFromFileImpl" - handles connection properties via ".properties" file excluding the need
 * to get into the code in order to change the environment.
 *
 * methods:
 * -load;
 * -getInstance;
 * -getConfigValue;
 * */
public class AppConfigFromFileImpl implements AppConfiguration {

    private static AppConfigFromFileImpl propertiesFileConfiguration;
    private Properties properties;

    private AppConfigFromFileImpl(Properties properties) throws IOException {
        this.properties = properties;
    }

    @Override
    public String getConfigValue(String key) {
        return properties.getProperty(key);
    }

    public static AppConfiguration getInstance() {
        if (propertiesFileConfiguration == null) {
            throw new IllegalArgumentException("Use method \"load\" for instance before use");
        }
        return propertiesFileConfiguration;
    }

    //"load" - loads data from .properties
    public static void load(String pathToFileWithProperties) throws IOException {
        if (propertiesFileConfiguration == null) {
            FromResourcesReader resourcesReader = new FromResourcesReader();

            File file = resourcesReader.getFileFromResources(pathToFileWithProperties);

            if (!file.exists()) {
                String errorMessage = "File \"" + pathToFileWithProperties + "\" not exists";
                throw new IOException(errorMessage);
            }

            try (InputStream inputStream = new FileInputStream(file)) {
                Properties localProperties = new Properties();
                localProperties.load(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                System.out.println(">>> Load properties from file success");
                System.out.println(" key = url       value = " + localProperties.getProperty("url"));
                System.out.println(" key = user      value = " + localProperties.getProperty("user"));
                System.out.println(" key = password  value = " + localProperties.getProperty("password"));
                propertiesFileConfiguration = new AppConfigFromFileImpl(localProperties);
            }
        }
    }
}
