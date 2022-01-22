package eu.nawiasdev.SafetySave;

import eu.nawiasdev.SafetySave.util.StreamGobbler;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class RebootTask extends TimerTask {
    private final boolean isWindows = System.getProperty("os.name").toLowerCase(Locale.ROOT).startsWith("windows");

    @Override
    public void run() {
        rebootWithRuntime();
    }

    private void rebootWithRuntime() {
        String shutdownCommand;
        Logger logger = Bukkit.getServer().getLogger();
        Runtime runtime = Runtime.getRuntime();
        if(isWindows) {
            shutdownCommand = "shutdown.exe /r /t 0";
            logger.info("Rebooting Windows");
        } else {
            logger.info("Rebooting Linux");
            shutdownCommand = "reboot";
        }
        try {
            runtime.exec(shutdownCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
