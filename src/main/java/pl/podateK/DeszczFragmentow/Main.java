package pl.podateK.DeszczFragmentow;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.podateK.DeszczFragmentow.Command.DeszczCommand;
import pl.podateK.DeszczFragmentow.Event.Event;
import pl.podateK.DeszczFragmentow.TabCompleater.TabCompleter;
import pl.podateK.DeszczFragmentow.Utill.Replace;

import java.io.File;

public final class Main extends JavaPlugin {

    public static BossBar bossBar;
    public static String bossBarName;
    public static BarColor bossBarColor;
    public static BarStyle bossBarStyle;
    public double chance = 0.25;
    public static Main plugin;
    public static boolean isDeszczOn = false;

    @Override
    public void onEnable() {
        plugin = this;
        getCommand("deszczfragmentow").setExecutor(new DeszczCommand());
        getCommand("deszczfragmentow").setTabCompleter(new TabCompleter());
        getServer().getPluginManager().registerEvents(new Event(plugin), this);
        File pluginFolder = getDataFolder();
        if (!pluginFolder.exists()) {
            pluginFolder.mkdir();
        }
        loadConfig();
        bossBar = getServer().createBossBar(Replace.fixColor(bossBarName), bossBarColor, bossBarStyle);
        if (isDeszczOn) {
            for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                bossBar.addPlayer(onlinePlayer);
            }
        }
        if (!isDeszczOn) {
            for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                bossBar.removePlayer(onlinePlayer);
                bossBar.removeAll();
            }
        }
    }
    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reloadConfig();

        FileConfiguration config = getConfig();
        chance = config.getDouble("settings.Szansa-na-Drop", 0.25);
        isDeszczOn = config.getBoolean("settings.Deszcz-Fragmentow-Wlaczony", false);
        bossBarName = config.getString("bossbar.nazwa");
        bossBarColor = BarColor.valueOf(config.getString("bossbar.kolor", "BLUE"));

        bossBarStyle = BarStyle.valueOf(config.getString("bossbar.typ", "SEGMENTED_20"));
    }

    @Override
    public FileConfiguration getConfig() {
        return super.getConfig();
    }
}
