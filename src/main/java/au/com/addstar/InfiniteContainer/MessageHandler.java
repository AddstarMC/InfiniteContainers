package au.com.addstar.InfiniteContainer;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static au.com.addstar.InfiniteContainer.InfiniteContainer.plugin;

/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 19/09/2017.
 */
public class MessageHandler {
    private ResourceBundle messages = ResourceBundle.getBundle("messages");

    public MessageHandler() {
        try {
            messages = ResourceBundle.getBundle("messages", Locale.getDefault());
        } catch (MissingResourceException e) {
            plugin.getServer().getLogger().info("Using fallback language profile no resource for your locale is available");
        }
    }

    private MessageFormat getMessageFormat(String key) {
        return new MessageFormat(messages.getString(key));
    }

    public String getMessage(String key){
        return getMessage(key, null);
    }

    public String getMessage(String key, String[] args) {
        if (args == null){
            return messages.getString(key);
        }else {
            MessageFormat mf = getMessageFormat(key);
            return mf.format(args);
        }
    }


}
