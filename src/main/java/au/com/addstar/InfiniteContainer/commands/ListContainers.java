package au.com.addstar.InfiniteContainer.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

import static au.com.addstar.InfiniteContainer.InfiniteContainer.plugin;

/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 17/09/2017.
 */
public class ListContainers implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        ArrayList<String> message =  new ArrayList<>();
        message.add(plugin.getMessage("ListTitle"));
        message.add("-----------------------------");
        for(Location container : plugin.getContainerManager().getContainers()){
            Location loc = container.getBlock().getLocation();
            Material type = loc.getBlock().getType();
            message.add(type.name() + " : " + loc.toString() );
        }
        message.add("-----------------------------");
        String[] result = new String[message.size()];
        message.toArray(result);
        commandSender.sendMessage(result);
        return true;
    }
}
