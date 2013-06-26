package yeller.app;

import java.util.ArrayList;
import java.util.List;
import java.util.EventListener;
import javax.swing.event.EventListenerList;
import yeller.model.*;
import yeller.util.JoinListener;
import yeller.util.Event;

/**
 *
 * @author Lars Steen
 */
public class Application 
{
    private User user;
    private Server server;
    private List<Channel> channels = new ArrayList<>();
    
    private EventListenerList listeners = new EventListenerList();
    
    public Application()
    {
        
    }
    
    public void run()
    {
        //Service.connect(this.server, this.user);
        //Service.join(this.channels);
        
        //while (this.server.isConnected())
        //{
        //    Message msg = Service.getMessage(this.server));
            
        //    if (msg.getCommand().equals("join"))
        //   {
        //        for (EventListener listener : this.listeners)
        //        {
        //            listener.notify();
        //        }
        //    }
            
        //    EventListener jl = new JoinListener();
        //}
    }
    
    public <T extends EventListener> void fire(Event event, Class<T> t)
    {
        EventListener[] listenerArray = this.listeners.getListeners(t);
        for(EventListener listener : listenerArray)
        {
            listener.notify();
        }
    }
}