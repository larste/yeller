package yeller;

import java.util.prefs.Preferences;

/**
 *
 * @author Steffen Christensen
 */
public class IrcPreferences {

    static Preferences prefs;

    public void setPreferences() {
        prefs = Preferences.userRoot();
        prefs = Preferences.userNodeForPackage(this.getClass());

    }
    
    public static Preferences getPrefs(){
        return prefs;
    }
    
    public static void putNick(String nick){
        prefs.put("nick", nick);
    }
    
    public static void putChannel(String channel){
        prefs.put("channel", channel);
    }
    
    public static String getNick(){
       String s = prefs.get("nick", "");
       return s;
    }
    public static String getChannel(){
        String s = prefs.get("channel", "");
        return s;
    }
}