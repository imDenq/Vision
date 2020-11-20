package fr.patatedouce.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class CC {

    public static String MENU_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "------------------------";
    public static String CHAT_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "------------------------------------------------";
    public static String MEDIUM_CHAT_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "------------------------------";

    public static String translate(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static List<String> translate(List<String> lines) {
        List<String> toReturn = new ArrayList<>();

        for (String line : lines) {
            toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
        }

        return toReturn;
    }

    public static List<String> translate(String[] lines) {
        List<String> toReturn = new ArrayList<>();

        for (String line : lines) {
            if (line != null) {
                toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
            }
        }

        return toReturn;
    }

    public static String formatTimeMillis(long millis) {
        long seconds = millis;

        if (seconds <= 0) {
            return "0 seconds";
        }

        long minutes = seconds / 60;
        seconds = seconds % 60;
        long hours = minutes / 60;
        minutes = minutes % 60;
        long day = hours / 24;
        hours = hours % 24;
        long years = day / 365;
        day = day % 365;

        StringBuilder time = new StringBuilder();

        if (years != 0) {
            time.append(years).append(years == 1 ? " year " : " years ");
        }

        if (day != 0) {
            time.append(day).append(day == 1 ? " day " : " days ");
        }

        if (hours != 0) {
            time.append(hours).append(hours == 1 ? " hour " : " hours ");
        }

        if (minutes != 0) {
            time.append(minutes).append(minutes == 1 ? " minute " : " minutes ");
        }

        if (seconds != 0) {
            time.append(seconds).append(seconds == 1 ? " second " : " seconds ");
        }

        return time.toString().trim();
    }
}
