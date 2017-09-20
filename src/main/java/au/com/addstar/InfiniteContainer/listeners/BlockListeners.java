package au.com.addstar.InfiniteContainer.listeners;

import au.com.addstar.InfiniteContainer.ContainerManager;
import org.bukkit.block.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;


import static au.com.addstar.InfiniteContainer.InfiniteContainer.plugin;

/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 17/09/2017.
 */
public class BlockListeners implements Listener {

    ContainerManager manager = plugin.getContainerManager();

    @EventHandler(priority= EventPriority.LOW,ignoreCancelled = true)
    public void onDispenseEvent(BlockDispenseEvent event){
        if(manager.hasContainer(event.getBlock().getLocation())){
            BlockState state = event.getBlock().getState();
            if(state instanceof Container){
                manager.refill((Container) state, event.getItem());
            }
        }
    }
    @EventHandler(priority= EventPriority.HIGH,ignoreCancelled = true)
    public void onContainerBreak(BlockBreakEvent e){
        if(manager.hasContainer(e.getBlock().getLocation())){;
        if(!e.getPlayer().hasPermission("infinitecontainer.icremove")){
            e.setCancelled(true);
            e.getPlayer().sendMessage(plugin.getMessage("NoPermissionRemove"));
        }
        if(manager.removeContainer(e.getBlock().getLocation())){
            e.getPlayer().sendMessage(plugin.getMessage("infiniteContainerRemoved") + e.getBlock().getLocation().toString());
        }
        }
    }

}
