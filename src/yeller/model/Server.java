package yeller.model;

import java.util.ArrayList;
import java.util.List;

public class Server
{
    private String name;
    private String address;
    
    private List<Channel> channels = new ArrayList<Channel>();
    private List<User> users = new ArrayList<User>();
}
