package eu.nawiasdev.SafetySave;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MessageOfTheDay extends PlayerListener {
    private static final String MESSAGE = ChatColor.GRAY+"[SafetySave] Server restart in "+ChatColor.DARK_GRAY;
    private final Calendar calendar;
    MessageOfTheDay(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        long diff = Math.abs(calendar.getTime().getTime() - new Date().getTime());
        long minutes = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
        long hours = TimeUnit.HOURS.convert(minutes, TimeUnit.MINUTES);
        minutes -= hours * 60L;
        player.sendMessage(MESSAGE + hours + "h " + minutes + "m");
        super.onPlayerJoin(event);
    }
}
