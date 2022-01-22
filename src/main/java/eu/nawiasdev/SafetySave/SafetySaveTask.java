package eu.nawiasdev.SafetySave;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.TimerTask;

public class SafetySaveTask extends TimerTask {
    private final Server server;
    SafetySaveTask(Server server){
        this.server = server;
    }
    @Override
    public void run() {
        //server.broadcastMessage(ChatColor.GRAY+"[SafetySave] Saving players...");
        server.savePlayers();
        server.getLogger().info("[SafetySave] Saved player data");
        //server.broadcastMessage(ChatColor.GRAY+"[SafetySave] Saved!");
    }
}
