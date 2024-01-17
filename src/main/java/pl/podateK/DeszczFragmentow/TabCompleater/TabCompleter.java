package pl.podateK.DeszczFragmentow.TabCompleater;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> lista = new java.util.ArrayList<>();
        if(args.length == 1) {
            lista.add("on");
            lista.add("off");
            lista.add("setodlamek");
            lista.add("dajodlamek");
            lista.add("reload");
        }
        return lista;
    }
}

