package pl.podateK.DeszczFragmentow.Command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.podateK.DeszczFragmentow.Main;
import pl.podateK.DeszczFragmentow.Utill.Replace;

import static pl.podateK.DeszczFragmentow.Main.*;

public class DeszczCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cNie mozesz uzyc tej komendy z konsoli!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§eUzycie: /deszcz <on/off/setodlamek/dajodlamek>");
            return true;
        }

        Player player = (Player) sender;

        if (args[0].equalsIgnoreCase("dajodlamek")) {
            if (!sender.hasPermission("lagoonbox.dajodlamek")) {
                sender.sendMessage("§cNie masz uprawnien do tej komendy!");
                return true;
            }
            if (args.length == 1) {
                sender.sendMessage("§eUzycie: /deszcz dajodlamek <gracz>");
                return true;
            }
            Player cel = Bukkit.getServer().getPlayer(args[1]);
            if (cel == null) {
                sender.sendMessage("§cNie znaleziono gracza!");
                return true;
            }
            if (plugin.getConfig().get("odlamek") == null) {
                sender.sendMessage("§cMusisz ustawic odlamek!");
                return true;
            }
            if (plugin.getConfig().get("odlamek") instanceof ItemStack) {
                ItemStack odlamek = (ItemStack) plugin.getConfig().get("odlamek");
                cel.getInventory().addItem(odlamek);
                sender.sendMessage("§aDano odlamek graczowi " + cel.getName());
                return true;
            } else {
                sender.sendMessage("§cMusisz ustawic odlamek!");
                return true;
            }
        }

        if (!sender.hasPermission("lagoonbox.deszcz")) {
            sender.sendMessage("§cNie masz uprawnien do tej komendy!");
            return true;
        }

        if (args[0].equalsIgnoreCase("setodlamek")) {
            if (sender.hasPermission("lagoonbox.setodlamek")) {
                if (player.getItemInHand() == null) {
                    sender.sendMessage("§cMusisz trzymac w rece odlamek!");
                    return true;
                }
                ItemStack odlamek = player.getItemInHand();
                plugin.getConfig().set("odlamek", odlamek);
                sender.sendMessage("§aPomyślnie Ustawiłeś Odłamek!");
                plugin.saveConfig();

                return true;
            } else {
                sender.sendMessage("§cNie masz uprawnien do tej komendy!");
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("on")) {
            if (plugin.getConfig().get("odlamek") == null) {
                sender.sendMessage("§cMusisz ustawic odlamek!");
                return true;
            }
            if (Main.isDeszczOn) {
                sender.sendMessage("§cDeszcz odlamkow jest juz wlaczony!");
                return true;
            }
            Main.isDeszczOn = true;

            bossBar.setTitle(Replace.fixColor(bossBarName));
            bossBar.setColor(bossBarColor);
            bossBar.setStyle(bossBarStyle);
            if (isDeszczOn) {
                for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                    bossBar.addPlayer(onlinePlayer);
                }
            }

            sender.sendMessage("§aWlaczono deszcz odlamkow!");
            return true;
        }

        if (args[0].equalsIgnoreCase("off")) {
            if (!Main.isDeszczOn) {
                for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                    bossBar.removePlayer(onlinePlayer);
                    bossBar.removeAll();
                }
                sender.sendMessage("§cDeszcz odlamkow jest juz wylaczony!");
                return true;

            }
            Main.isDeszczOn = false;
            if (!Main.isDeszczOn) {
                for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                    bossBar.removePlayer(onlinePlayer);
                    bossBar.removeAll();
                }
            }
            sender.sendMessage("§aWylaczono deszcz odlamkow!");
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            Main.plugin.reloadConfig();

            sender.sendMessage("§aPomyślnie Preładowano Plugin!");

        }

        return true;
    }
}
