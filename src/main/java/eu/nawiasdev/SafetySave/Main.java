package eu.nawiasdev.SafetySave;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    private final Server server = Bukkit.getServer();
    private final Logger logger = server.getLogger();
    private final Timer safetySaveTimer = new Timer("SafetySaveTimer");
    private final boolean isWindows = System.getProperty("os.name").toLowerCase(Locale.ROOT).startsWith("windows");

    @Override
    public void onEnable() {
        PluginDescriptionFile pluginDescriptionFile = this.getDescription();
        logger.info(pluginDescriptionFile.getFullName() + "Enabled");
        //5 minutes
        long period = 1000L * 60L * 5L;
        safetySaveTimer.scheduleAtFixedRate(new SafetySaveTask(server), 0, period);
    }

    @Override
    public void onDisable() {
        PluginDescriptionFile pluginDescriptionFile = this.getDescription();
        safetySaveTimer.cancel();
        logger.info(pluginDescriptionFile.getFullName() + "Disabled");
    }
}
