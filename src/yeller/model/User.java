package yeller.model;

import java.util.ArrayList;
import java.util.List;
import yeller.util.Receivable;

/**
 *
 * @author Lars Steen
 */
public class User implements Receivable
{
    private String nick;
    private String hostname;
    
    private Server server;
    private List<Channel> channels = new ArrayList<>();

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
