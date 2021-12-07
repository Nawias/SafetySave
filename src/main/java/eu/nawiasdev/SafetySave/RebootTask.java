package eu.nawiasdev.SafetySave;

import java.util.Locale;
import java.util.TimerTask;

public class RebootTask extends TimerTask {
    private final boolean isWindows = System.getProperty("os.name").toLowerCase(Locale.ROOT).startsWith("windows");

    @Override
    public void run() {
        ProcessBuilder builder = new ProcessBuilder();
        if(isWindows) {
            builder.command("cmd.exe","shutdown /r -t 0");
        } else {
            builder.command("sh", "reboot");
        }
    }
}
