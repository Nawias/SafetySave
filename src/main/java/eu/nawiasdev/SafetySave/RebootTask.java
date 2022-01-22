package eu.nawiasdev.SafetySave;

import eu.nawiasdev.SafetySave.util.StreamGobbler;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class RebootTask extends TimerTask {
    private final boolean isWindows = System.getProperty("os.name").toLowerCase(Locale.ROOT).startsWith("windows");

    @Override
    public void run() {
        Bukkit.getServer().savePlayers();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.kickPlayer("Server is rebooting");
        }
        Bukkit.getServer().getWorlds().get(0).save();
        rebootWithRuntime();
        ConsoleCommandSender sender = new ConsoleCommandSender(Bukkit.getServer());
        Bukkit.getServer().dispatchCommand(sender,"stop");
    }

    private void rebootWithRuntime() {
        String shutdownCommand;
        Runtime runtime = Runtime.getRuntime();
        if(isWindows) {
            shutdownCommand = "shutdown.exe /r /t 60";
        } else {
            shutdownCommand = "shutdown -r +1";
        }
        try {
            runtime.exec(shutdownCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
