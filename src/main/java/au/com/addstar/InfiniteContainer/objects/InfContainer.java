package au.com.addstar.InfiniteContainer.objects;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.beans.Transient;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 18/09/2017.
 */
public class InfContainer implements ConfigurationSerializable{
    private List<ItemStack> contents;
    private boolean automatic;
    private int taskId;
    private Long time;

    public InfContainer(Map<String,Object> serialized){
       this((List<ItemStack>)serialized.get("contents"),(boolean)serialized.get("automatic"),(long)serialized.get("time"));
    }

    public InfContainer(List<ItemStack> contents, boolean automatic, long time) {
        this.contents = contents;
        this.automatic = automatic;
        this.time = time;
        taskId = -1;
    }
    public InfContainer(ItemStack[] contents, boolean automatic, long time) {
        this.contents = Arrays.asList(contents);
        this.automatic = automatic;
        this.time = time;
        taskId = -1;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    /**
     * Returns a clone of the stored inventory
     * @return {@link ItemStack[]} a clone of the stack.
     */
    public ItemStack[] getContents() {
        ItemStack[] result = new ItemStack[contents.size()];
        contents.toArray(result);
        return result.clone();
    }

    /**
     * Clones the param and stores the clone;
     *
     * @param contents {@link ItemStack[]}
     */

    public void setContents(ItemStack[] contents) {
        this.contents = Arrays.asList(contents.clone());
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
