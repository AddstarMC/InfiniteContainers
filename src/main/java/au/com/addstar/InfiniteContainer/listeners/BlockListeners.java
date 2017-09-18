package au.com.addstar.InfiniteContainer.listeners;

import au.com.addstar.InfiniteContainer.ContainerManager;
import org.bukkit.block.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;


import static au.com.addstar.InfiniteContainer.InfiniteContainer.plugin;

/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 17/09/2017.
 */
public class BlockListeners implements Listener {

    @EventHandler(priority= EventPriority.LOW,ignoreCancelled = true)
    public void onDispenseEvent(BlockDispenseEvent event){
        BlockState state = event.getBlock().getState();
        if(state instanceof Container){
            Inventory now = ((Container) state).getSnapshotInventory();
            if(plugin.getContainerManager().hasContainer((Container)state)){
                ContainerManager.refill((Container) state, event.getItem());
                state.update();
            }
        }
    }
}
