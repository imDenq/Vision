package fr.patatedouce.listener;

import fr.patatedouce.Vision;
import fr.patatedouce.data.PlaytimePlayerData;
import fr.patatedouce.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerPlaytimeListener implements Listener {

    @EventHandler
    public void loadPlayerData(AsyncPlayerPreLoginEvent event) {
        if (Vision.getInstance().getPlayerListData().isInList(event.getUniqueId().toString())) {
            PlaytimePlayerData playtimePlayerData = PlaytimePlayerData.getByUuid(event.getUniqueId());
            if (playtimePlayerData == null) {
                playtimePlayerData = new PlaytimePlayerData(event.getName(), event.getUniqueId());
            }
            if (!playtimePlayerData.isDataLoaded()) {
                playtimePlayerData.loadData();
            }
        }
    }

    // DOne xd

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (Vision.getInstance().getPlayerListData().isInList(event.getPlayer().getUniqueId().toString())) {
            PlaytimePlayerData playtimePlayerData = PlaytimePlayerData.getByUuid(event.getPlayer().getUniqueId());
            if (playtimePlayerData == null) {
                event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
                event.setKickMessage(CC.translate("&cPlease reconnect."));
            }
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        if (Vision.getInstance().getPlayerListData().isInList(event.getPlayer().getUniqueId().toString())) {
            Player player = event.getPlayer();
            PlaytimePlayerData data = PlaytimePlayerData.getByUuid(player.getUniqueId());
            if (data != null) {
                data.saveData();
            }
        }
    }
}
