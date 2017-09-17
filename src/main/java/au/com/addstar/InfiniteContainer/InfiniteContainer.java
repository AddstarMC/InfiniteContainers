package au.com.addstar.InfiniteContainer;

import au.com.addstar.InfiniteContainer.commands.CreateInfiniteContainer;
import au.com.addstar.InfiniteContainer.commands.ListContainers;
import au.com.addstar.InfiniteContainer.commands.RemoveInifiniteContainer;
import au.com.addstar.InfiniteContainer.listeners.BlockListeners;
import au.com.addstar.InfiniteContainer.listeners.PlayerListeners;
import org.bstats.bukkit.Metrics;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 15/09/2017.
 */
public class InfiniteContainer extends JavaPlugin {

    public static InfiniteContainer plugin;
    private ResourceBundle messages = ResourceBundle.getBundle("messages");
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
        cManager = new ContainerManager(new File(plugin.getDataFolder(), "containers.json"));
        cManager.load();
        Metrics metrics = new Metrics(this);
        try {
            messages = ResourceBundle.getBundle("messages", Locale.getDefault());
        } catch (MissingResourceException e) {
            this.getServer().getLogger().info("Using fallback language profile no resource for your locale is available");
        }
        pListen = new PlayerListeners();
        this.getServer().getPluginCommand("icadd").setExecutor(new CreateInfiniteContainer(this));
        this.getServer().getPluginCommand("icremove").setExecutor(new RemoveInifiniteContainer(this));
        this.getServer().getPluginCommand("iclist").setExecutor(new ListContainers());

        this.getServer().getPluginManager().registerEvents(new BlockListeners(), this);

    }

    @Override
    public void onDisable() {
        cManager.save();
        HandlerList.unregisterAll(this);
    }

    public String getMessage(String key) {
        return messages.getString(key);
    }

    public PlayerManager getPlayerManager() {
        return pManager;
    }


}
