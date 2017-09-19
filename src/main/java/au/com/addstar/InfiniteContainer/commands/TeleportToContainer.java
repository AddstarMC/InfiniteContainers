package au.com.addstar.InfiniteContainer.commands;

import au.com.addstar.InfiniteContainer.InfiniteContainer;
import au.com.addstar.InfiniteContainer.utilities.LocationUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import static au.com.addstar.InfiniteContainer.InfiniteContainer.plugin;

/**
 * Created for the AddstarMC Server Network
 * Created by Narimm on 18/09/2017.
 */
public class TeleportToContainer implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            final Player player  = (Player) sender;
            if (args.length == 0 ){
                return false;
            }
            int i =1;
            int num = Integer.parseInt(args[0]);
            Location loc = null;
            for(Location container : plugin.getContainerManager().getContainers()){
                if(i==num) {
                    loc = container.getBlock().getLocation();
                }
                i++;
            }
            if(loc != null) {
                Location newLoc = LocationUtils.getSafeDestination(loc);
                Location teleport = LocationUtils.lookAt(newLoc, loc);
                player.teleport(teleport, PlayerTeleportEvent.TeleportCause.PLUGIN);
                sender.sendMessage(InfiniteContainer.plugin.getMessage("TeleportedTo",String.valueOf(num)));
                return true;
            }else{
                sender.sendMessage(InfiniteContainer.plugin.getMessage("TeleportLocationNull",String.valueOf(num)));
                return false;
            }
        }else{
            sender.sendMessage(InfiniteContainer.plugin.getMessage("PlayerCommandOnly"));
            return true;
        }
    }
}
