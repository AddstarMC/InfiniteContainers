package au.com.addstar.InfiniteContainer;

import au.com.addstar.InfiniteContainer.objects.Action;
import au.com.addstar.InfiniteContainer.objects.ICPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 16/09/2017.
 */
public class PlayerManager {
    private Map<UUID, ICPlayer> players;

    PlayerManager() {
        this.players = new HashMap<>();
    }

    public boolean hasPlayer(Player player){
        return players.containsKey(player.getUniqueId());
    }

    public Action getPlayerAction(Player player){
        return players.get(player.getUniqueId()).getAction();
    }

    public String[] getArgs(Player player){
        return players.get(player.getUniqueId()).getArgs();
    }

    public void addPlayer(ICPlayer player){
         players.put(player.getPlayer().getUniqueId(),player);
    }
    public void removePlayer(Player player){
        players.remove(player.getUniqueId());
    }

    public static void commandTimeOut(final CommandSender sender){
        Bukkit.getScheduler().scheduleSyncDelayedTask(InfiniteContainer.plugin, () -> {
            ArrayList<RegisteredListener> listeners = HandlerList.getRegisteredListeners(InfiniteContainer.plugin);
            for (RegisteredListener rList : listeners){
                if (rList.getListener() == InfiniteContainer.plugin.getPlayerListener()){
                    HandlerList.unregisterAll(InfiniteContainer.plugin.getPlayerListener());
                    sender.sendMessage(InfiniteContainer.plugin.getMessage("TimeOut"));
                }
            }
        },400);
    }
}

