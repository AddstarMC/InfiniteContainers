package au.com.addstar.InfiniteContainer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Container;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.*;


/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 16/09/2017.
 */
public class ContainerManager {
    private File saveFile;
    private YamlConfiguration config;
    LinkedHashMap<Location, ItemStack[]> containers;

    ContainerManager(File save) {
        this.containers = new LinkedHashMap<>();
        saveFile = save;
        config = new YamlConfiguration();
    }

    public LinkedHashSet<Location> getContainers() {
        LinkedHashSet<Location> result = new LinkedHashSet<>();
        for(Map.Entry<Location,ItemStack[]> e: containers.entrySet()){
            result.add(e.getKey());
        }
        return result;
    }

    public boolean addContainer(Container container){
        ItemStack[] contents = container.getSnapshotInventory().getContents().clone();
        return (containers.put(container.getLocation(),contents)==contents);
    }

    public boolean removeContainer(Container container){
        return(containers.remove(container.getLocation())!=null);

    }
    public void save(){

        Map<Location,ItemStack[]> out = new HashMap<>();
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
            Map<Location,ItemStack[]> configContainers = (Map<Location, ItemStack[]>)obj;
            containers.putAll(configContainers);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }


    }

    public boolean hasContainer(Container container) {
        return containers.containsKey(container.getLocation());
    }
     public void refill(Container container) {
         refill(container,null);
    }

    public void refill(Container container, ItemStack dispensed){
        if(dispensed != null) {
            container.getInventory().setContents(containers.get(container.getLocation()));
            container.update(true);
            return;
        }
        ItemStack[] orig = containers.get(container.getLocation());
        for(ItemStack item: orig){
            item.setAmount(container.getInventory().getMaxStackSize());
        }
        container.getInventory().setContents(orig);
        container.update(true);//force an update
    }

}
