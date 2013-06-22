package yeller;

import yeller.service.Service;
import yeller.model.Message;
import yeller.gui.ChatFrame;
import yeller.gui.SettingFrame;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import yeller.model.Channel;
import yeller.model.Server;
import yeller.service.Connection;

/**
 *
 * @author Steffen Christensen
 */
public class Main {

    public static ChatFrame mf;
    public static SettingFrame sf;
    public static DefaultListModel listModel;
    public static String[] people;
    public static String his;
    public static HTMLEditorKit kit = new HTMLEditorKit();
    public static HTMLDocument doc = new HTMLDocument();
    public static BufferedReader reader;
    public static BufferedWriter writer;
    public static String nick;
    public static String login;
    public static String msg;
    public static Channel channel;
    public static DefaultListModel dlModel;

    public static void main(String[] args) throws Exception {

        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("com.sun.java.swing.plaf.gtk.GTKLookAndFeel".equals(info.getClassName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }

        sf = new SettingFrame(mf, true);
        mf = new ChatFrame();

        sf.setVisible(true);
        listModel = new DefaultListModel();
        dlModel = new DefaultListModel();
        mf.listPeople.setModel(listModel);
        mf.setVisible(true);

        // The server to connect to and our details.        
        Server server = new Server();
        server.setAddress("irc.freenode.net");
        
        // Define the channel:
        ArrayList<Channel>channels = new ArrayList();
        channel = new Channel();
        channel.setName(sf.txfChannel.getText());
        channels.add(channel);

        nick = sf.txfNick.getText();
        login = sf.txfNick.getText();
        String prevLine;

        // GUI elements pending to be removed from Main()
        mf.lblHeader.setText(channel.getName()); 
        
        Connection connection = new Connection(server, nick);
        connection.joinServer();
    }
    
    
    /*
     * This should NOT be here!
     */
    public static void displayMyMessage(String myMessage) throws IOException, BadLocationException {
        String s = nick.toString();
        String sendNick = "<b><h2>" + s + "</h2></b>";
        dlModel.addElement(sendNick);
        dlModel.addElement(myMessage);
        ChatFrame.listContent.setModel(dlModel);
    }
}
