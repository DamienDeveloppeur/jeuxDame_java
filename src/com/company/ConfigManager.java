package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static Properties properties;
    private final static String VARIABLE_NON_TROUVE = "La variable cherché n'a pas était trouvé";

    static {
        InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties");
        properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getProperty(String key){
        if(!properties.containsKey(key)) throw new RuntimeException(VARIABLE_NON_TROUVE);
        else return properties.getProperty(key);
    }

    public static String recupererVariableGlobale(String variable) {
        return ConfigManager.getProperty(variable);
    }
}
