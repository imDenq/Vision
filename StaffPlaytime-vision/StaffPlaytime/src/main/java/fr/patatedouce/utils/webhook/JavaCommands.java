package fr.patatedouce.utils.webhook;

import lombok.Getter;
import org.bukkit.Bukkit;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

@Getter
public class JavaCommands {

    public void restart() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            List<String> args = ManagementFactory.getRuntimeMXBean().getInputArguments();
            List<String> command = new ArrayList<>();
            command.add(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java.exe");
            for (int i = 0; i < args.size(); i++) {
                command.add(args.get(i));
            }
            command.add("-jar");
            command.add(new File(Bukkit.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getAbsolutePath());
            try {
                new ProcessBuilder(command).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        Bukkit.shutdown();
    }
}
