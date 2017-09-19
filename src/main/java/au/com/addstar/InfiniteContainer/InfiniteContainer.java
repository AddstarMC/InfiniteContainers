package au.com.addstar.InfiniteContainer;

import au.com.addstar.InfiniteContainer.commands.*;
import au.com.addstar.InfiniteContainer.listeners.BlockListeners;
import au.com.addstar.InfiniteContainer.listeners.PlayerListeners;
import org.bstats.bukkit.Metrics;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 15/09/2017.
 */
public class InfiniteContainer extends JavaPlugin {

    public static InfiniteContainer plugin;
    private MessageHandler messages = new MessageHandler();
    private PlayerListeners pListen;
    private PlayerManager pManager;
    private ContainerManager cManager;

    public PlayerListeners getPlayerListener() {
        return pListen;
    }

    public ContainerManager getContainerManager() {
        return cManager;
    }

    @Override
    public void onEnable() {
        plugin = this;
        pManager = new PlayerManager();
        cManager = new ContainerManager(new File(plugin.getDataFolder(), "containers.yml"));
        cManager.load();
        Metrics metrics = new Metrics(this);

        pListen = new PlayerListeners();
        //Register Commands
        this.getServer().getPluginCommand("icadd").setExecutor(new CreateInfiniteContainer(this));
        this.getServer().getPluginCommand("icupdate").setExecutor(new UpdateContainer());
        this.getServer().getPluginCommand("icremove").setExecutor(new RemoveInifiniteContainer(this));
        this.getServer().getPluginCommand("iclist").setExecutor(new ListContainers());
        this.getServer().getPluginCommand("icteleport").setExecutor(new TeleportToContainer());
        //Register Listeners
        this.getServer().getPluginManager().registerEvents(new BlockListeners(), this);

    }

    @Override
    public void onDisable() {
        cManager.save();
        HandlerList.unregisterAll(this);
    }
    public String getMessage(String key){
      return messages.getMessage(key);
    }

    public String getMessage(String key, String arg){
        String[] args = new String[1];
        Collections.singleton(arg).toArray(args);
        return getMessage(key, args);
    }

    public String getMessage(String key, String[] args) {
        return messages.getMessage(key, args);
    }

    public PlayerManager getPlayerManager() {
        return pManager;
    }


}
