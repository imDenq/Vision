package fr.patatedouce.action;

import fr.patatedouce.Vision;
import fr.patatedouce.data.PlayerOfflineData;
import fr.patatedouce.data.PlaytimePlayerData;
import fr.patatedouce.utils.CC;
import fr.patatedouce.utils.command.BaseCommand;
import fr.patatedouce.utils.command.Command;
import fr.patatedouce.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaytimeDebugCommand extends BaseCommand {
    @Command(name = "debugplaytime", aliases = {"debugpt", "playtimedebug"}, permission = "vision.debug")

    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        PlaytimePlayerData playerData;
        if (args.length == 0) {
            playerData = PlaytimePlayerData.getByUuid(player.getUniqueId());
            if (playerData == null) return;
            player.sendMessage(CC.CHAT_BAR);
            player.sendMessage(CC.translate("&b&lVision &7(Vision) &8- &fv" + Vision.getInstance().getDescription().getVersion()));
            player.sendMessage(CC.CHAT_BAR);
            player.sendMessage(CC.translate("&bDebug"));
            player.sendMessage(CC.translate("&8 * &7Sur la liste&f: " + (Vision.getInstance().getPlayerListData().isInList(player.getUniqueId().toString()) ? "&aOui" : "&cNon")));
            player.sendMessage(CC.translate("&8 * &7Temps de jeu&f: &b" + CC.formatTimeMillis(playerData.getPlaytime())));
            player.sendMessage(CC.CHAT_BAR);
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target != null && target.isOnline()) {
            playerData = PlaytimePlayerData.getByUuid(target.getUniqueId());
            if (playerData == null) return;
            player.sendMessage(CC.CHAT_BAR);
            player.sendMessage(CC.translate("&b&lVision &7(Vision) &8- &fv" + Vision.getInstance().getDescription().getVersion()));
            player.sendMessage(CC.CHAT_BAR);
            player.sendMessage(CC.translate("&bDebug"));
            player.sendMessage(CC.translate("&8 * &7Sur la liste&f: " + (Vision.getInstance().getPlayerListData().isInList(target.getUniqueId().toString()) ? "&aOui" : "&cNon")));
            player.sendMessage(CC.translate("&8 * &7Temps de jeu&f: &b" + CC.formatTimeMillis(playerData.getPlaytime())));
            player.sendMessage(CC.CHAT_BAR);
        } else {
            String targetOffline = args[0];
            if (PlayerOfflineData.isHasData(targetOffline) && PlayerOfflineData.isOnList(targetOffline)) {
                player.sendMessage(CC.CHAT_BAR);
                player.sendMessage(CC.translate("&b&lVision &7(Vision) &8- &fv" + Vision.getInstance().getDescription().getVersion()));
                player.sendMessage(CC.CHAT_BAR);
                player.sendMessage(CC.translate("&bDebug"));
                player.sendMessage(CC.translate("&8 * &7Sur la liste&f: " + (PlayerOfflineData.isOnList(targetOffline) ? "&aOui" : "&cNon")));
                player.sendMessage(CC.translate("&8 * &7Temps de jeu&f: &b" + PlayerOfflineData.getTimePlayer(targetOffline)));
                player.sendMessage(CC.CHAT_BAR);
            }
        }
    }
}
