package fr.patatedouce.task;

import fr.patatedouce.data.PlaytimePlayerData;
import lombok.SneakyThrows;
import org.bukkit.scheduler.BukkitRunnable;

public class PlaytimeTask extends BukkitRunnable {

    @SneakyThrows
    @Override
    public void run() {
        for (PlaytimePlayerData playtimePlayerData : PlaytimePlayerData.getPlayersCountingPlaytime()) {
            if (playtimePlayerData != null) {
                playtimePlayerData.setPlaytime(playtimePlayerData.getPlaytime() + 1L);
            }
        }
    }
}
