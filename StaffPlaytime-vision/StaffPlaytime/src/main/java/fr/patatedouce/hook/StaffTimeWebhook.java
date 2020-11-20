package fr.patatedouce.hook;

import fr.patatedouce.Vision;
import fr.patatedouce.data.PlayerListData;
import fr.patatedouce.data.PlayerOfflineData;
import fr.patatedouce.utils.CC;
import fr.patatedouce.utils.webhook.DiscordWebhook;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.awt.*;
import java.io.IOException;
import java.util.UUID;

public class StaffTimeWebhook {

    public void sendStaffTime() {
        try {
            DiscordWebhook webhook = new DiscordWebhook(Vision.getInstance().getMainConfig().getString("WEBHOOK.URL"));
            webhook.setAvatarUrl(Vision.getInstance().getMainConfig().getString("WEBHOOK.IMAGE"));
            webhook.setUsername("Staff Play Time");
            if (!PlayerListData.playersList.isEmpty()) {
                for (String string : PlayerListData.playersList) {
                    OfflinePlayer targetData = Bukkit.getOfflinePlayer(UUID.fromString(string));
                    if (targetData != null) {
                        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                .addField("Nom:", targetData.getName(), false)
                                .addField("Temps:", PlayerOfflineData.getTimePlayer(targetData.getName()), false)
                                .setColor(new Color(103, 225, 255))
                                .setThumbnail("https://minotar.net/avatar/" + targetData.getName()));
                    }
                }
            }
            webhook.execute(); //Handle exception
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(CC.translate("&cErreure dans le webhook"));
        }
    }
}
