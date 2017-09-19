package au.com.addstar.InfiniteContainer.listeners;

import au.com.addstar.InfiniteContainer.ContainerManager;
import au.com.addstar.InfiniteContainer.objects.InfContainer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Nameable;
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
                    String[] args;
                    boolean auto = false;
                    long time = 0L;
                    switch(plugin.getPlayerManager().getPlayerAction(event.getPlayer())){
                        case ADD:
                            args = plugin.getPlayerManager().getArgs(event.getPlayer());
                            if(args.length > 1)auto = Boolean.parseBoolean(args[0]);
                            if(auto && args.length > 2)time = Long.parseLong(args[1])*20;
                            plugin.getContainerManager().addContainer((Container) state,auto,time);
                                plugin.getContainerManager().refill(((Container) state));
                                plugin.getPlayerManager().removePlayer(event.getPlayer());
                                if(state instanceof Nameable){
                                    ((Nameable) state).setCustomName("Infinite Container");
                                }
                                HandlerList.unregisterAll(this);
                                plugin.getContainerManager().save();
                                event.getPlayer().sendMessage(plugin.getMessage("infiniteContainerAdded",event.getClickedBlock().getLocation().toString()));
                                event.setCancelled(true);
                            break;
                        case REMOVE:
                            if(plugin.getContainerManager().hasContainer((Container) state)) {
                                InfContainer iContainer = plugin.getContainerManager().getInfiniteContainer((Container) state);
                                if (iContainer.isAutomatic()) Bukkit.getScheduler().cancelTask(iContainer.getTaskId());
                                plugin.getContainerManager().removeContainer((Container) state);
                                if(state instanceof Nameable){
                                    ((Nameable) state).setCustomName(null);
                                }
                                plugin.getPlayerManager().removePlayer(event.getPlayer());
                                HandlerList.unregisterAll(this);
                                plugin.getContainerManager().save();

                                event.getPlayer().sendMessage(plugin.getMessage("infiniteContainerRemoved",event.getClickedBlock().getLocation().toString()));
                                event.setCancelled(true);
                            }
                            break;
                        case UPDATE:
                            args = plugin.getPlayerManager().getArgs(event.getPlayer());
                            if(args.length > 1)auto = Boolean.parseBoolean(args[0]);
                            if(auto && args.length > 2)time = Long.parseLong(args[1])*20;
                            if(plugin.getContainerManager().hasContainer(event.getClickedBlock().getLocation())){
                                InfContainer container = plugin.getContainerManager().getInfiniteContainer(event.getClickedBlock().getLocation());
                                if(auto && time > 0L){
                                    container.setAutomatic(true);
                                    container.setTime(time);
                                    container.setContents(((Container)event.getClickedBlock().getState()).getSnapshotInventory().getContents());
                                    plugin.getContainerManager().setAutomaticOn((Container) event.getClickedBlock().getState(), container);
                                    String[] m1 = new String[]{"True",String.valueOf(time)};
                                    event.getPlayer().sendMessage(plugin.getMessage("InfiniteContainerUpdated",m1));
                                }else{
                                    plugin.getContainerManager().cancelAutomatic(container);
                                    String[] m2 = new String[]{"False","0"};
                                    event.getPlayer().sendMessage(plugin.getMessage("InfiniteContainerUpdated",m2));
                                }
                            }
                            break;
                            default:
                    }
                }
            }
        }
    }
}
