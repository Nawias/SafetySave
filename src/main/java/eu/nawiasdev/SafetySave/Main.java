package eu.nawiasdev.SafetySave;

import eu.nawiasdev.SafetySave.util.ConfigUtil;
import eu.nawiasdev.SafetySave.util.FileTreeInitializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    private final Server server = Bukkit.getServer();
    private final Logger logger = server.getLogger();
    private final Timer safetySaveTimer = new Timer("SafetySaveTimer");
    private final Timer rebootTimer = new Timer("RebootTimer");

    @Override
    public void onEnable() {
        FileTreeInitializer.initialize();
        ConfigUtil.initialize();
        PluginDescriptionFile pluginDescriptionFile = this.getDescription();
        logger.info(pluginDescriptionFile.getFullName() + " Enabled");
        setupSafetySave();
        setupReboot();
    }

    private void setupReboot() {
        if (ConfigUtil.SHOULD_REBOOT) {
            Calendar calendar = getCalendar();
            logger.info("The server will reboot on "+ calendar.getTime().toString());
            rebootTimer.schedule(new RebootTask(),calendar.getTime());
            PluginManager pluginManager = server.getPluginManager();
            pluginManager.registerEvent(
                    Event.Type.PLAYER_JOIN,
                    new MessageOfTheDay(calendar),
                    Event.Priority.Normal,
                    this
            );
        }
    }

    private void setupSafetySave() {
        long period = 1000L * 60L * ConfigUtil.PERIOD;
        safetySaveTimer.scheduleAtFixedRate(new SafetySaveTask(server), period, period);
    }

    private Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance(ConfigUtil.TIMEZONE);
        calendar.set(Calendar.HOUR, 4);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        if(new Date().getTime() > calendar.getTime().getTime())
            calendar.add(Calendar.DATE,1);
        return calendar;
    }

    @Override
    public void onDisable() {
        PluginDescriptionFile pluginDescriptionFile = this.getDescription();
        safetySaveTimer.cancel();
        rebootTimer.cancel();
        logger.info(pluginDescriptionFile.getFullName() + "Disabled");
    }
}
