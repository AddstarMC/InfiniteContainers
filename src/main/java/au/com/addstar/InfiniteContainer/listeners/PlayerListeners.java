package au.com.addstar.InfiniteContainer.listeners;

import au.com.addstar.InfiniteContainer.ContainerManager;
import au.com.addstar.InfiniteContainer.objects.InfContainer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Nameable;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
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

    private ContainerManager manager = plugin.getContainerManager();
    private final ArrayList blocks = new ArrayList<>(
            Arrays.asList(
                    Material.DISPENSER,
                    Material.DROPPER)
    );
    @EventHandler(priority=EventPriority.LOW,ignoreCancelled = true)
    public void onRightClickContainer(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (plugin.getPlayerManager().hasPlayer(player)) {

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Block block = event.getClickedBlock();
                if (blocks.contains(block.getType())) {
                    BlockState state = block.getState();
                    String[] args;
                    boolean auto = false;
                    long time = 0L;
                    Container container = (Container) state;
                    switch(plugin.getPlayerManager().getPlayerAction(player)){
                        case ADD:
                            args = plugin.getPlayerManager().getArgs(player);
                            if(args.length > 1)auto = Boolean.parseBoolean(args[0]);
                            if(auto && args.length > 2)time = Long.parseLong(args[1])*20;
                            manager.addContainer(container,auto,time);
                            manager.refill(container);
                            if(manager.hasContainer(block.getLocation())) {
                                plugin.getPlayerManager().removePlayer(player);
                                if (state instanceof Nameable) {
                                    ((Nameable) state).setCustomName("Infinite Container");
                                }
                                HandlerList.unregisterAll(this);
                                player.sendMessage(plugin.getMessage("infiniteContainerAdded",block.getLocation().toString()));
                                event.setCancelled(true);
                            }
                            manager.save();
                            break;
                        case REMOVE:
                            if(plugin.getContainerManager().hasContainer(block.getLocation())){
                                InfContainer iContainer = manager.getInfiniteContainer(container);
                                if (iContainer.isAutomatic()) manager.cancelAutomatic(iContainer);
                                manager.removeContainer(block.getLocation());
                                if(state instanceof Nameable){
                                    ((Nameable) state).setCustomName(null);
                                }
                                plugin.getPlayerManager().removePlayer(player);
                                HandlerList.unregisterAll(this);
                                manager.save();

                                player.sendMessage(plugin.getMessage("infiniteContainerRemoved",block.getLocation().toString()));
                                event.setCancelled(true);
                            }
                            manager.save();
                            break;
                        case UPDATE:
                            args = plugin.getPlayerManager().getArgs(player);
                            if(args.length > 1)auto = Boolean.parseBoolean(args[0]);
                            if(auto && args.length > 2)time = Long.parseLong(args[1])*20;
                            if(manager.hasContainer(block.getLocation())){
                                InfContainer icontainer = plugin.getContainerManager().getInfiniteContainer(block.getLocation());
                                String[] m1;
                                if(auto && time > 0L){
                                    icontainer.setAutomatic(true);
                                    icontainer.setTime(time);
                                    icontainer.setContents(container.getSnapshotInventory().getContents());
                                    manager.refill(container);
                                    manager.setAutomaticOn(container, icontainer);
                                    m1 = new String[]{"True",String.valueOf(time)};
                                }else{
                                    manager.cancelAutomatic(icontainer);
                                    icontainer.setContents(container.getSnapshotInventory().getContents());//cloned
                                    manager.refill(container);
                                    m1 = new String[]{"False","0"};
                                }
                                player.sendMessage(plugin.getMessage("InfiniteContainerUpdated",m1));
                                event.setCancelled(true);
                                HandlerList.unregisterAll(this);
                            }
                            manager.save();
                            break;
                            default:
                    }
                }
            }
        }
    }
}
