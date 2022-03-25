package org.glockinmybape.tattymysql;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Logger {
    public static boolean debug = false;

    public static void info(String text) {
        Bukkit.getConsoleSender().sendMessage("§b(" + TattyMYSQL.inst.getDescription().getName() + "/INFO) " + text);
    }

    public static void warn(String text) {
        Bukkit.getConsoleSender().sendMessage("§b(" + TattyMYSQL.inst.getDescription().getName() + "/WARN) " + text);
    }

    public static void error(String text) {
        Bukkit.getConsoleSender().sendMessage("§4(" + TattyMYSQL.inst.getDescription().getName() + "/ERROR) " + text);
    }

    public static void info(JavaPlugin plugin, Object text) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[" + plugin.getName() + "] " + text);
    }

    public static void warning(JavaPlugin plugin, Object text) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[" + plugin.getName() + "] " + text);
    }

    public static void error(JavaPlugin plugin, Object text) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[" + plugin.getName() + "] " + text);
    }

    public static void debug(JavaPlugin plugin, Object text) {
        if (debug) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "[" + plugin.getName() + "] " + text);
        }
    }
}
