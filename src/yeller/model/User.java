package yeller.model;

import java.util.ArrayList;
import java.util.List;
import yeller.util.Receivable;

public class User implements Receivable
{
    private String nick;
    private String hostname;
    
    private Server server;
    private List<Channel> channels = new ArrayList<Channel>();
}
