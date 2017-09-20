package au.com.addstar.InfiniteContainer;

import au.com.addstar.InfiniteContainer.objects.InfContainer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Container;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.io.*;
import java.util.*;

import static au.com.addstar.InfiniteContainer.InfiniteContainer.plugin;


/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 16/09/2017.
 */
public class ContainerManager {
    private File saveFile;
    private YamlConfiguration config;
    LinkedHashMap<Location, InfContainer> containers;

    ContainerManager(File save) {
        this.containers = new LinkedHashMap<>();
        saveFile = save;
        config = new YamlConfiguration();
    }

    public LinkedHashSet<Location> getContainers() {
        LinkedHashSet<Location> result = new LinkedHashSet<>();
        for(Map.Entry<Location,InfContainer> e: containers.entrySet()){
            result.add(e.getKey());
        }
        return result;
    }

    public boolean addContainer(Container container,boolean automatic,long time){
        ItemStack[] contents = container.getSnapshotInventory().getContents().clone();
        if(time == 0L) automatic=false;
        InfContainer infiniteContainer = new InfContainer(contents,automatic,time);
        containers.put(container.getLocation(),infiniteContainer);
        refill(container);
        infiniteContainer.setContents(container.getInventory().getContents());
        if(automatic){
            setAutomaticOn(container,infiniteContainer  );
        }
        return (containers.get(container.getLocation()) == infiniteContainer);
    }

    public boolean removeContainer(Location location){
        return(containers.remove(location)!=null);
    }
    public void save(){

        Map<Location,InfContainer> out = new HashMap<>();
        out.putAll(containers);
        config.set("containers", out);
        try {
            config.save(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    void load() {
        Object obj = null;
        try {
            config.load(saveFile);
        } catch (FileNotFoundException e) {
            plugin.getLogger().info("The containers file does not yet exist");
        } catch (InvalidConfigurationException|IOException e){
            e.printStackTrace();
        }
        try{
            obj = config.get("containers");
            Map<Location, InfContainer> configContainers = null;
            if (obj != null) {
                configContainers = (Map<Location, InfContainer>) obj;
            }
            if(configContainers != null)containers.putAll(configContainers);
        }catch (ClassCastException e){
            plugin.getLogger().info(ChatColor.BOLD + "There are no current containers");
            plugin.getLogger().info((obj != null)?"Object:" + obj.toString():"Object : null");
            plugin.getLogger().info(e.getLocalizedMessage());
            plugin.getLogger().info(Arrays.toString(e.getStackTrace()));
        }
    }
    public InfContainer getInfiniteContainer(Container container){
        return containers.get(container.getLocation());
    }
    public InfContainer getInfiniteContainer(Location location){
        return containers.get(location);
    }
    public boolean hasContainer(Container container) {
        return hasContainer(container.getLocation());
    }
    public boolean hasContainer(Location location) {
        return containers.containsKey(location);
    }
    public void refill(Container container) {
         refill(container,null);
    }

    public void refill(Container container, ItemStack dispensed){
        InfContainer iContainer = containers.get(container.getLocation());
        if(dispensed != null) {
            container.getInventory().setContents(iContainer.getContents());
            container.update(true);
            return;
        }
        ItemStack[] orig = iContainer.getContents();//its a clone
        for(ItemStack item: orig){
            if(item != null)item.setAmount(container.getInventory().getMaxStackSize());
        }
        iContainer.setContents(orig); //again a clone;
        container.getInventory().setContents(orig);//not a clone
        container.update(true);//force an update
    }

    public void setAutomaticOn(final Container container,InfContainer iContainer){
        if(!iContainer.isAutomatic() && !(iContainer.getTime() > 0L)){
            Bukkit.getLogger().warning(plugin.getMessage("ContainerNotAutomatic"));
        }
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new ContainerAutomation(container),iContainer.getTime(),iContainer.getTime());
        if(taskId == -1) {
            Bukkit.getLogger().warning(plugin.getMessage("TaskScheduleFailed"));
            iContainer.setAutomatic(false);
            iContainer.setTime(0L);
          }
        iContainer.setTaskId(taskId);
    }
    public void cancelAutomatic(InfContainer container){
        container.setAutomatic(false);
        container.setTime(0L);
        if(container.getTaskId() != -1)Bukkit.getScheduler().cancelTask(container.getTaskId());
    }

    private class ContainerAutomation implements Runnable {
        private final Container container;

        public ContainerAutomation(final Container container) {
            this.container = container;
        }

        @Override
        public void run() {
            if(container instanceof Dispenser){
                ((Dispenser) container).dispense();
            }
            if(container instanceof Dropper){
                ((Dropper) container).drop();
            }
        }
    }



}
