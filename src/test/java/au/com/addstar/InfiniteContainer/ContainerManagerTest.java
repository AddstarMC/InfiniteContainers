package au.com.addstar.InfiniteContainer;

import au.com.addstar.InfiniteContainer.objects.InfContainer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.spec.ECField;
import java.util.Map;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 17/09/2017.
 */
@SuppressWarnings("unused")
public class ContainerManagerTest {
    private ContainerManager manager;
    private World world;
    private Location loc;
    private InfContainer container;
    @Test
    public void save() throws Exception {
        manager.save();
        assertTrue(manager.containers.size() == 2);
        assertTrue(manager.containers.get(loc) == container);
    }
    @Test
    public void load() {
        manager.save();
        manager.containers = null;
        File dir = new File(System.getProperty("java.io.tmpdir"));
        File saveFile = new File(dir,"configuration.yml");
        ConfigurationSerialization.registerClass(InfContainer.class);
        ConfigurationSerialization.registerClass(Location.class);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(saveFile);
        ConfigurationSerialization.registerClass(InfContainer.class);
        Object obj = config.get("containers");
        Map<Location, InfContainer> configContainers = null;
        if (obj != null) {
            configContainers = (Map<Location, InfContainer>) obj;
        }
        if(configContainers != null)manager.containers.putAll(configContainers);
        //manager.load();
        assertTrue(manager.getContainers().size() == 2);
        assertTrue(manager.containers.get(loc) == container);
    }


    @Before
    public void setUp() throws Exception {
        File dir = new File(System.getProperty("java.io.tmpdir"));
        File saveFile = new File(dir,"configuration.yml");
        if(saveFile.exists() && !saveFile.delete()) throw new FileNotFoundException();
        manager = new ContainerManager(saveFile);
        world = mock(World.class);
        when(world.deserialize()).thenReturn(world);
        when(world.getName()).thenReturn("testWorld");
        loc = createLocation(0,0,0);
        Location loc2 = createLocation(1,2,3);
        ItemStack apple = createItem(Material.APPLE);
        ItemStack carrot = createItem(Material.CARROT_ITEM);
        ItemStack potato = createItem(Material.POTATO_ITEM);
        ItemStack[] contents = new ItemStack[]{
                apple,
                carrot
        };
        ItemStack[] c2 = new ItemStack[]{
                potato
        };
        container = new InfContainer(contents,true, 300L);
        manager.containers.put(loc,container);
        InfContainer container2 = new InfContainer(c2,true, 200L);
        manager.containers.put(loc2,container2);
    }

    private ItemStack createItem(Material mat){
        ItemStack item = mock(ItemStack.class);
        item.setAmount(1);
        item.setType(mat);
        when(item.getType()).thenReturn(mat);
        when(item.getAmount()).thenReturn(1);
        when(item.getData()).thenReturn(new MaterialData(mat));
        return item;
    }

    private Location createLocation(int x, int y, int z){
       return new Location(world,x, y, z );
    }


}