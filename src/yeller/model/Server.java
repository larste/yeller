package yeller.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lars Steen
 */
public class Server
{
    private String name;
    private String address;
    
    private List<Channel> channels = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
