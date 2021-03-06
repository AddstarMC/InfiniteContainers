package au.com.addstar.InfiniteContainer;

import org.bukkit.Location;
import org.bukkit.block.Container;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;


/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 16/09/2017.
 */
public class ContainerManager {
    private File saveFile;
    private YamlConfiguration config;
    LinkedHashSet<Location> containers;

    ContainerManager(File save) {
        this.containers = new LinkedHashSet<>();
        saveFile = save;
        config = new YamlConfiguration();
    }

    public LinkedHashSet<Location> getContainers() {
        return containers;
    }

    public boolean addContainer(Container container){
        return addLocation(container.getLocation());
    }

    boolean addLocation(Location location){
       return containers.add(location);
    }

    public boolean removeContainer(Container container){
        return containers.remove(container.getLocation());
    }
    public void save(){

        List<Location> out = new ArrayList<>();
        out.addAll(containers);
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
            List<Location> locs = (List<Location>)config.getList("containers");
            containers.addAll(locs);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }


    }

    public boolean hasContainer(Container container) {
        return containers.contains(container.getLocation());
    }
     public static void refill(Container container) {
         refill(container,null);
    }

    public static void refill(Container container, ItemStack dispensed){
        if(dispensed != null) {
            dispensed.setAmount(container.getInventory().getMaxStackSize());
            container.getInventory().addItem(dispensed);
            container.update(true);//force it to update
            return;
        }
        for(ItemStack item :  container.getInventory()){
            if(item != null) {
                int max = item.getMaxStackSize();
                if (max < 2) max =  container.getInventory().getMaxStackSize();
                item.setAmount(max);
            }
        }
        container.update(true);//force an update
    }

}
