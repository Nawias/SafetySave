package eu.nawiasdev.SafetySave.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.TimeZone;
import java.util.function.Function;

import static eu.nawiasdev.SafetySave.util.FileTreeInitializer.PLUGIN_DIR;

public final class ConfigUtil {
    public static final String PROPERTIES_FILE = PLUGIN_DIR +"/plugin.properties";
    public static int PERIOD = 5;
    public static boolean SHOULD_REBOOT = false;
    public static TimeZone TIMEZONE = TimeZone.getDefault();
    public static int HOUR = 4;
    public static int MINUTE = 0;

    /**
     * Generic function for parsing props when these props may be empty
     * @param parser Parsing function that takes a String
     * @param defaultValue The value that should be used in case prop is empty
     * @param value The value to be parsed
     * @param <T> Return type of the parsed prop
     * @return Parsed prop of type <T> if the prop was valid, otherwise returns the default value.
     */
    private static <T> T tryParseProp(Function<String, T> parser, T defaultValue, String value) {
        try {
            return parser.apply(value);
        } catch (Exception ignored) {}
        return defaultValue;
    }

    public static void initialize(){
        Properties properties = new Properties();
        try{
            InputStream inputStream = new FileInputStream(PROPERTIES_FILE);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PERIOD = tryParseProp(Integer::parseInt, PERIOD, properties.getProperty("period"));
        SHOULD_REBOOT = tryParseProp(Boolean::parseBoolean, SHOULD_REBOOT, properties.getProperty("should-reboot"));
        TimeZone tz = TimeZone.getTimeZone(properties.getProperty("timezone"));
        if (!tz.equals(TimeZone.getTimeZone("GMT"))){
            TIMEZONE = tz;
        }
        HOUR = tryParseProp(Integer::parseInt, HOUR, properties.getProperty("hour"));
        MINUTE = tryParseProp(Integer::parseInt, MINUTE, properties.getProperty("minute"));
    }
}
