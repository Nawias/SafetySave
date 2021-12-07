package eu.nawiasdev.SafetySave.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.TimeZone;

import static eu.nawiasdev.SafetySave.util.FileTreeInitializer.PLUGIN_DIR;

public final class ConfigUtil {
    public static final String PROPERTIES_FILE = PLUGIN_DIR +"/plugin.properties";
    public static int PERIOD = 5;
    public static boolean SHOULD_REBOOT = false;
    public static TimeZone TIMEZONE = TimeZone.getDefault();
    public static void initialize(){
        Properties properties = new Properties();
        try{
            InputStream inputStream = new FileInputStream(PROPERTIES_FILE);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PERIOD = Integer.parseInt(properties.getProperty("port"));
        SHOULD_REBOOT = Boolean.parseBoolean(properties.getProperty("jwt-secret"));
        TimeZone tz = TimeZone.getTimeZone(properties.getProperty("timezone"));
        if (!tz.equals(TimeZone.getTimeZone("GMT"))){
            TIMEZONE = tz;
        }
    }
}
