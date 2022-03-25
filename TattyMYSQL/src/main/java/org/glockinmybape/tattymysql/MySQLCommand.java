package org.glockinmybape.tattymysql;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MySQLCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("mMySQL.admin")) {
            sender.sendMessage(ChatColor.RED + "У вас недостаточно прав.");
            return true;
        } else if (args.length == 0) {
            this.help(sender, command, label, args);
            return true;
        } else {
            String var5 = args[0].toLowerCase();
            byte var6 = -1;
            switch(var5.hashCode()) {
                case -892481550:
                    if (var5.equals("status")) {
                        var6 = 0;
                    }
                default:
                    switch(var6) {
                        case 0:
                            this.status(sender, command, label, args);
                            break;
                        default:
                            this.unknown(sender, command, label, args);
                    }

                    return true;
            }
        }
    }

    private void status(CommandSender sender, Command command, String label, String[] args) {
        Iterator var5 = MySQL.users.iterator();

        while(var5.hasNext()) {
            MySQL m = (MySQL)var5.next();
            if (m.getOnline() == 0L) {
                sender.sendMessage(ChatColor.GOLD + "  from " + ChatColor.GREEN + String.format("%1$-14s", m.getPlugin().getName()) + ChatColor.RED + " offline");
            } else {
                sender.sendMessage(ChatColor.GOLD + "  from " + ChatColor.GREEN + String.format("%1$-14s", m.getPlugin().getName()) + ChatColor.YELLOW + String.format("%1$-20s", "executed: " + m.getSended()) + String.format("%1$-20s", "pps: " + (new BigDecimal((double)m.getSended() / (double)((System.currentTimeMillis() - m.getOnline()) / 1000L))).setScale(2, RoundingMode.HALF_UP)));
            }
        }

    }

    private void help(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("");
        sender.sendMessage("§b§lTattyMYSQL");
        sender.sendMessage("");
        sender.sendMessage("§b| §7Показать статус активных соеденений - §f/mysql status");
        sender.sendMessage("");
    }

    private void unknown(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.RED + "Неизвестная подкоманда");
    }
}
