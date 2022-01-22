package eu.nawiasdev.SafetySave.util;


import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public final class FileTreeInitializer {

    public static final String PLUGIN_DIR = "plugins/SafetySave";

    public static void initialize() {
        File properties = new File(PLUGIN_DIR +"/plugin.properties");
        try {
            if(!properties.getParentFile().exists())
                properties.getParentFile().mkdirs();
            if(!properties.exists()) {
                String propData = "period=5\nreboot=true";
                properties.createNewFile();
                Files.write(properties.toPath(),propData.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

