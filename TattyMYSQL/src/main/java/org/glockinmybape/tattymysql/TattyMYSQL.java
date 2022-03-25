package org.glockinmybape.tattymysql;

import jdk.tools.jmod.Main;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;


public class TattyMYSQL extends JavaPlugin {
    public static JavaPlugin plugin;
    public static TattyMYSQL inst;

    public void onEnable() {
        long time = System.currentTimeMillis();
        plugin = this;
        this.getCommand("mysql").setExecutor(new MySQLCommand());
        inst = this;
        Logger.info("§b");
        Logger.info("§b .----------------------------------------------------------. ");
        Logger.info("§b| .-------------------------------------------------------. |");
        Logger.info("§b| |             \t\t\t\t\t\t");
        Logger.info("§b| |            §7Плагин: §bTattyMYSQL§8| §7Версия: §b1.0                ");
        Logger.info("§b| |        §7Создан для §bTattyWorld §8- §7Разработал: §bglockinmybape\t");
        Logger.info("§b| |                    §bvk.com/TattyWorld");
        Logger.info("§b| |             \t\t\t\t\t\t");
        Logger.info("§b| '-------------------------------------------------------'§b|");
        Logger.info("§b'-----------------------------------------------------------'");
        Logger.info("§b");
}

    public String configString(String path) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString(path));
    }
}
