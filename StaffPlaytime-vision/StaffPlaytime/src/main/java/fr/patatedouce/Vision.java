package fr.patatedouce;

import fr.patatedouce.action.PlaytimeCommand;
import fr.patatedouce.action.PlaytimeDebugCommand;
import fr.patatedouce.data.PlayerListData;
import fr.patatedouce.data.PlaytimePlayerData;
import fr.patatedouce.database.MongoDB;
import fr.patatedouce.hook.StaffTimeWebhook;
import fr.patatedouce.listener.PlayerPlaytimeListener;
import fr.patatedouce.task.PlaytimeTask;
import fr.patatedouce.utils.command.CommandFramework;
import fr.patatedouce.utils.file.ConfigFile;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

@Getter @Setter
public final class Vision extends JavaPlugin {

    @Getter public static Vision instance;

    @Getter private ConfigFile mainConfig;
    private CommandFramework framework;
    private PlaytimePlayerData playtimePlayerData;
    private PlayerListData playerListData;
    private MongoDB mongoDB;
    private StaffTimeWebhook staffTimeWebhook;

    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;

        this.mainConfig = new ConfigFile(this, "config");
        this.playerListData = new PlayerListData();
        this.staffTimeWebhook = new StaffTimeWebhook();
        framework = new CommandFramework(this);
        mongoDB = new MongoDB();
        mongoDB.connect();
        playerListData.load();
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerPlaytimeListener(), this);

        new PlaytimeCommand();
        new PlaytimeDebugCommand();
        new PlaytimeTask().runTaskTimer(this, 0, 20L);
        Bukkit.getServer().getConsoleSender().sendMessage("[Vision - Playtime] Started counting players playtime.");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                staffTimeWebhook.sendStaffTime();
            }
        }, new SimpleDateFormat("HH:mm").parse("00:00"));
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            PlaytimePlayerData playerData = PlaytimePlayerData.getByUuid(player.getUniqueId());
            if (playerData != null) playerData.saveData();
        });
        playerListData.save();

        instance = null;
    }
}
