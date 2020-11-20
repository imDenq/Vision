package fr.patatedouce.action;

import fr.patatedouce.Vision;
import fr.patatedouce.data.PlayerListData;
import fr.patatedouce.data.PlayerOfflineData;
import fr.patatedouce.data.PlaytimePlayerData;
import fr.patatedouce.utils.CC;
import fr.patatedouce.utils.command.BaseCommand;
import fr.patatedouce.utils.command.Command;
import fr.patatedouce.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlaytimeCommand extends BaseCommand {
    @Command(name = "playtime", aliases = {"vision"})

    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.CHAT_BAR);
            player.sendMessage(CC.translate("&b&lVision &7(Vision) &8- &fv" + Vision.getInstance().getDescription().getVersion()));
            player.sendMessage(CC.CHAT_BAR);
            player.sendMessage(CC.translate("&bAvailable Commands"));
            player.sendMessage(CC.translate("&8 * &f/vision list"));
            player.sendMessage(CC.translate("&8 * &f/vision add &3<joueur>"));
            player.sendMessage(CC.translate("&8 * &f/vision remove &3<joueur>"));
            player.sendMessage(CC.CHAT_BAR);
            return;
        }
        Player target = null;
        switch (args[0]) {
            case "list":
                player.sendMessage(CC.CHAT_BAR);
                player.sendMessage(CC.translate("&b&lVision &7(Vision) &8- &fv" + Vision.getInstance().getDescription().getVersion()));
                player.sendMessage(CC.CHAT_BAR);
                player.sendMessage(CC.translate("&bPlaytime"));
                for (String string : PlayerListData.playersList) {
                    OfflinePlayer targetData = Bukkit.getOfflinePlayer(UUID.fromString(string));
                    if (targetData != null) {
                        if (targetData.isOnline()) {
                            player.sendMessage(CC.translate("&8 * &b" + targetData.getName() + "&7 | &fTemps joué&7: &b" + CC.formatTimeMillis(PlaytimePlayerData.getByName(targetData.getName()).getPlaytime())));
                        } else {
                            player.sendMessage(CC.translate("&8 * &b" + targetData.getName() + "&7 | &fTemps joué&7: &b" + PlayerOfflineData.getTimePlayer(targetData.getName())));
                        }
                    }
                }
                player.sendMessage(CC.CHAT_BAR);
                Vision.getInstance().getStaffTimeWebhook().sendStaffTime();
                break;

            case "add":
                if (args.length < 1) return;

                target = Bukkit.getPlayer(args[0]);
                if (!PlayerListData.getPlayersList().contains(target.getUniqueId())) {
                    PlayerListData.getPlayersList().add(target.getUniqueId().toString());
                    player.sendMessage(CC.translate("&a" + target.getName() + " &3a été ajouté a la liste."));
                    PlaytimePlayerData playtimePlayerData = PlaytimePlayerData.getByUuid(target.getUniqueId());
                    if (playtimePlayerData == null) {
                        playtimePlayerData = new PlaytimePlayerData(target.getName(), target.getUniqueId());
                    }
                    if (!playtimePlayerData.isDataLoaded()) {
                        playtimePlayerData.loadData();
                    }
                } else {
                    player.sendMessage(CC.translate("&cCe joueur est déjà dans la liste."));
                }
                break;
            case "remove":
                if (args.length < 1) return;

                target = Bukkit.getPlayer(args[0]);
                if (PlayerListData.getPlayersList().contains(target.getUniqueId().toString())) {
                    PlayerListData.getPlayersList().remove(target.getUniqueId().toString());
                    player.sendMessage(CC.translate("&a" + target.getName() + " &3a été ajouté a la liste."));
                } else {
                    player.sendMessage(CC.translate("&cCe joueur n'est pas dans la liste."));
                }
                break;
            default:
                player.sendMessage(CC.CHAT_BAR);
                player.sendMessage(CC.translate("&b&lVision &7(Vision) &8- &fv" + Vision.getInstance().getDescription().getVersion()));
                player.sendMessage(CC.CHAT_BAR);
                player.sendMessage(CC.translate("&bCommandes:"));
                player.sendMessage(CC.translate("&8 * &f/vision list"));
                player.sendMessage(CC.translate("&8 * &f/vision add &3<joueur>"));
                player.sendMessage(CC.translate("&8 * &f/vision remove &3<joueur>"));
                player.sendMessage(CC.CHAT_BAR);
                break;
        }
    }
}
