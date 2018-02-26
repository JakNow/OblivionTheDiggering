package pl.oblivion.core.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    private static final Logger logger = Logger.getLogger(Config.class.getName());

    public static Properties loadProperties(String file) {
        try {
            //
            InputStream stream = Files.newInputStream(Paths.get(file));
            Properties properties = new Properties();
            properties.load(stream);
            logger.info("Loaded " + file + " file.");
            return properties;
        } catch (IOException e) {
            logger.fatal("Couldn't load " + file + " for core!", e);
        }
        return null;
    }

}
