package pl.podateK.DeszczFragmentow.Event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.podateK.DeszczFragmentow.Main;

import java.util.Random;

import static pl.podateK.DeszczFragmentow.Main.bossBar;

public class Event implements Listener {
    final Random random = new Random();
    public Event(Main plugin) {}

    @SuppressWarnings("all")
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player deadPlayer = e.getEntity();
        Player killer = deadPlayer.getKiller();

        if (killer != null || Main.isDeszczOn) {
            double randomValue = random.nextDouble();
            if (killer instanceof Player) {
                if (deadPlayer instanceof Player) {
                    if (randomValue < Main.plugin.chance) {
                        killer.getInventory().addItem(Main.plugin.getConfig().getItemStack("odlamek"));
                        killer.sendMessage("§a Pomyślnie otrzymałeś fragment oceanu za zabicie gracza");
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (Main.isDeszczOn) {
            bossBar.addPlayer(event.getPlayer());
        } else if (!Main.isDeszczOn) {

            bossBar.removePlayer(event.getPlayer());
            bossBar.removeAll();

        }
    }
}