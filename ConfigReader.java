package com.parabank.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    
    private static Properties properties;
    
    static {
        try {
            String configPath = System.getProperty("user.dir") + "/src/test/resources/config.properties";
            FileInputStream fis = new FileInputStream(configPath);
            properties = new Properties();
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            System.out.println("Config file not found, using default values");
            properties = new Properties();
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}