package au.com.addstar.InfiniteContainer.objects;

import org.bukkit.entity.Player;

/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 16/09/2017.
 */
public class ICPlayer {
    public Player getPlayer() {
        return player;
    }

    public Action getAction() {
        return action;
    }

    private Player player;

    private Action action;


    public ICPlayer(Player player) {
        this.player = player;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
