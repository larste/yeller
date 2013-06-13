package yeller.model;

import java.util.ArrayList;
import java.util.List;
import yeller.util.Receivable;

public class Channel implements Receivable
{
    private String name;
    private String topic;

    private Server server;
    private List<User> users = new ArrayList<User>();
    private List<Message> messages = new ArrayList<Message>();
}