package au.com.addstar.InfiniteContainer.commands;

import au.com.addstar.InfiniteContainer.InfiniteContainer;
import au.com.addstar.InfiniteContainer.PlayerManager;
import au.com.addstar.InfiniteContainer.objects.Action;
import au.com.addstar.InfiniteContainer.objects.ICPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 15/09/2017.
 */
public class CreateInfiniteContainer implements CommandExecutor {
    private final InfiniteContainer plugin;

    public CreateInfiniteContainer(InfiniteContainer plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            final Player player  = (Player) sender;
            ICPlayer icPlayer = new ICPlayer(player, Action.ADD, args);
            plugin.getPlayerManager().addPlayer(icPlayer);
            plugin.getServer().getPluginManager().registerEvents(plugin.getPlayerListener(),plugin);
            sender.sendMessage(plugin.getMessage("PlayerActionAdd"));
            PlayerManager.commandTimeOut(sender);
            return true;
        }else{
            sender.sendMessage(InfiniteContainer.plugin.getMessage("PlayerCommandOnly"));
            return false;
        }
    }
}
