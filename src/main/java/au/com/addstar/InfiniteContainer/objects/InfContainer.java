package au.com.addstar.InfiniteContainer.objects;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 18/09/2017.
 */
public class InfContainer implements ConfigurationSerializable{
    private ItemStack[] contents;
    private boolean automatic;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    private Integer taskId = null;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    private Long time;

    public InfContainer(Map<String,Object> serialized){
       this((ItemStack[])serialized.get("contents"),(boolean)serialized.get("automatic"),(long)serialized.get("time"));
    }

    public InfContainer(ItemStack[] contents, boolean automatic, long time) {
        this.contents = contents;
        this.automatic = automatic;
        this.time = time;
    }

    public ItemStack[] getContents() {
        return contents.clone();
    }

    public void setContents(ItemStack[] contents) {
        this.contents = contents.clone();
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String,Object> out = new HashMap<>();
        out.put("contents",contents);
        out.put("automatic", automatic);
        out.put("time", time);
        return out;
    }
}
