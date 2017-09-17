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
 * Created by benjamincharlton on 16/09/2017.
 */
public class RemoveInifiniteContainer implements CommandExecutor {
    private InfiniteContainer plugin;

    public RemoveInifiniteContainer(InfiniteContainer plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(strings.length > 1 && strings[0].equals("all")){
            plugin.getContainerManager().getContainers().clear();
            plugin.getContainerManager().save();
            if(plugin.getContainerManager().getContainers().size() == 0)sender.sendMessage(plugin.getMessage("InfiniteClear"));
        }
        if(sender instanceof Player){
            Player player  = (Player) sender;
            ICPlayer icPlayer = new ICPlayer(player);
            icPlayer.setAction(Action.REMOVE);
            plugin.getPlayerManager().addPlayer(icPlayer);
            sender.sendMessage(plugin.getMessage("PlayerActionRemove"));
            plugin.getServer().getPluginManager().registerEvents(plugin.getPlayerListener(),plugin);
            PlayerManager.commandTimeOut(sender);
        }else{
            sender.sendMessage(InfiniteContainer.plugin.getMessage("PlayerCommandOnly"));
            return true;
        }
        return false;
    }
}
