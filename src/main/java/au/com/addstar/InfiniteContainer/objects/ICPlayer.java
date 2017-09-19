package au.com.addstar.InfiniteContainer.objects;

import org.bukkit.entity.Player;

/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 16/09/2017.
 */
public class ICPlayer {
    private final Player player;
    private Action action;
    private String[] args;

    public ICPlayer(Player player, Action action, String[] args) {
        this.player = player;
        this.action = action;
        setArgs(args);
    }

    public Player getPlayer() {
        return player;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
