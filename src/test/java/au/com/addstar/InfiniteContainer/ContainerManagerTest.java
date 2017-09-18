package au.com.addstar.InfiniteContainer;

import org.bukkit.Location;
import org.bukkit.World;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 17/09/2017.
 */
@SuppressWarnings("unused")
public class ContainerManagerTest {
    private ContainerManager manager;
    private World world;
    @Test
    public void save() throws Exception {
        manager.save();
        assertTrue(manager.containers.size() == 1);
    }

    public void load() {
        manager.save();
        manager.containers = null;
        manager.load();
        assertTrue(manager.getContainers().size() == 1);
        manager.addLocation(new Location(world,1,1,1));
        assertTrue(manager.getContainers().size() == 2);

    }


    @Before
    public void setUp() throws Exception {
        File dir = new File(System.getProperty("java.io.tmpdir"));
        File saveFile = new File(dir,"configuration.yml");
        if(saveFile.exists())saveFile.delete();
        manager = new ContainerManager(saveFile);
        world = mock(World.class);
        when(world.getName()).thenReturn("testWorld");
        Location loc = new Location(world,0, 0, 0 );
        manager.addLocation(loc);
    }

}