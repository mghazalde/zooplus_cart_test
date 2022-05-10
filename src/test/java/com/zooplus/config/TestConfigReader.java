package com.zooplus.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class TestConfigReader {
    Logger logger = LoggerFactory.getLogger(TestConfigReader.class);
    public static Properties APPLICATION_CONFIG = new Properties();

    static {
        try {
            File f = new File(TestConfigReader.class.getClassLoader().getResource("applicationConfig.properties").getFile());
            FileReader fr = new FileReader(f);
            APPLICATION_CONFIG.load(fr);
        }catch (Exception e){
            throw new RuntimeException("Error while loading configurations");
        }
    }
    
    public static String get(String key) {
        return APPLICATION_CONFIG.get(key).toString();
    }
}
