package au.com.addstar.InfiniteContainer.listeners;

import au.com.addstar.InfiniteContainer.ContainerManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Arrays;

import static au.com.addstar.InfiniteContainer.InfiniteContainer.plugin;

/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 17/09/2017.
 */
public class PlayerListeners implements Listener {
    private final ArrayList blocks = new ArrayList<>(
            Arrays.asList(
                    Material.DISPENSER,
                    Material.DROPPER)
    );
    @EventHandler(priority=EventPriority.LOW,ignoreCancelled = true)
    public void onRightClickContainer(PlayerInteractEvent event) {
        if (plugin.getPlayerManager().hasPlayer(event.getPlayer())) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Block block = event.getClickedBlock();
                if (blocks.contains(block.getType())) {
                    BlockState state = block.getState();
                    switch(plugin.getPlayerManager().getPlayerAction(event.getPlayer())){
                        case ADD:
                            if(plugin.getContainerManager().addContainer((Container) state)) {
                                plugin.getContainerManager().refill(((Container) state));
                                plugin.getPlayerManager().removePlayer(event.getPlayer());
                                HandlerList.unregisterAll(this);
                                plugin.getContainerManager().save();
                                event.getPlayer().sendMessage(plugin.getMessage("infiniteContainerAdded") + event.getClickedBlock().getLocation().toString());
                                event.setCancelled(true);
                            }
                            break;
                        case REMOVE:
                            if(plugin.getContainerManager().removeContainer((Container) state)) {
                                plugin.getPlayerManager().removePlayer(event.getPlayer());
                                HandlerList.unregisterAll(this);
                                plugin.getContainerManager().save();
                                event.getPlayer().sendMessage(plugin.getMessage("infiniteContainerRemoved") + event.getClickedBlock().getLocation().toString());
                                event.setCancelled(true);
                            }
                            break;
                    }
                }
            }
        }
    }
}
