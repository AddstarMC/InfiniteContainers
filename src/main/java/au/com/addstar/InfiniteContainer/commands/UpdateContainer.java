package au.com.addstar.InfiniteContainer.commands;

import au.com.addstar.InfiniteContainer.InfiniteContainer;
import au.com.addstar.InfiniteContainer.PlayerManager;
import au.com.addstar.InfiniteContainer.objects.Action;
import au.com.addstar.InfiniteContainer.objects.ICPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static au.com.addstar.InfiniteContainer.InfiniteContainer.plugin;

/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 19/09/2017.
 */
public class UpdateContainer implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            final Player player  = (Player) sender;
            ICPlayer icPlayer = new ICPlayer(player, Action.UPDATE, args);
            plugin.getPlayerManager().addPlayer(icPlayer);
            plugin.getServer().getPluginManager().registerEvents(plugin.getPlayerListener(),plugin);
            sender.sendMessage(plugin.getMessage("PlayerActionUpdate"));
            PlayerManager.commandTimeOut(sender);
            return true;
        }else{
            sender.sendMessage(plugin.getMessage("PlayerCommandOnly"));
            return true;
        }
    }
}
