package au.com.addstar.InfiniteContainer;

import au.com.addstar.InfiniteContainer.objects.InfContainer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Container;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.configuration.InvalidConfigurationException;
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
        if(automatic){
            setAutomaticOn(container,infiniteContainer  );
        }
        return (containers.get(container.getLocation()) == infiniteContainer);
    }

    public boolean removeContainer(Container container){
        return removeContainer(container.getLocation());
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
        try {
            config.load(saveFile);
            Object obj = config.get("containers", new HashMap<Location,ItemStack[]>());
            Map<Location, InfContainer> configContainers = null;
            if(obj != null) {
                configContainers = (Map<Location, InfContainer>) obj;
            }
            containers.putAll(configContainers);
        } catch (IOException | InvalidConfigurationException|ClassCastException e) {
            e.printStackTrace();
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
        if(dispensed != null) {
            container.getInventory().setContents(containers.get(container.getLocation()).getContents());
            container.update(true);
            return;
        }
        ItemStack[] orig = containers.get(container.getLocation()).getContents();
        for(ItemStack item: orig){
            item.setAmount(container.getInventory().getMaxStackSize());
        }
        containers.get(container.getLocation()).setContents(orig);
        container.getInventory().setContents(orig);
        container.update(true);//force an update
    }

    public void setAutomaticOn(final Container container,InfContainer iContainer){
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if(container instanceof Dispenser){
                ((Dispenser) container).dispense();
            }
            if(container instanceof Dropper){
                ((Dropper) container).drop();
            }
        },iContainer.getTime(),iContainer.getTime());
        iContainer.setTaskId(taskId);
    }

    public void cancelAutomatic(InfContainer container){
        container.setAutomatic(false);
        container.setTime(0L);
        Bukkit.getScheduler().cancelTask(container.getTaskId());
    }


}
