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
        ProcessBuilder builder = new ProcessBuilder();
        Bukkit.getServer().savePlayers();
        Bukkit.getServer().getWorlds().get(0).save();
        Logger logger = Bukkit.getServer().getLogger();
        if(isWindows) {
            builder.command("cmd.exe","shutdown /r -t 0");
        } else {
            builder.command("sh", "reboot");
        }
        builder.directory(new File(System.getProperty("user.home")));
        try {
            Process process = builder.start();
            StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), logger::info);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            int exitCode = process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
