package yeller;

import yeller.service.Service;
import yeller.model.Message;
import yeller.gui.ChatFrame;
import yeller.gui.SettingFrame;
import java.io.*;
import java.net.*;
import javax.swing.DefaultListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

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
    public static String channel;
    public static BufferedReader reader;
    public static BufferedWriter writer;
    public static String nick;
    public static String msg;
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
//        mf.txtServerOutput.setEditorKit(kit);
//        mf.txtServerOutput.setDocument(doc);
//
//
//        DefaultCaret caret = (DefaultCaret) mf.txtServerOutput.getCaret();
//        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);


        // The server to connect to and our details.

        String server = "irc.freenode.net";

        nick = sf.txfNick.getText();

        String login = sf.txfNick.getText();

        String prevLine;



        // The channel which the client will join.

        channel = sf.txfChannel.getText();
        mf.lblHeader.setText(channel);



        // Connect directly to the IRC server.

        Socket socket = new Socket(server, 6667);

        writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()));

        reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));



        // Log on to the server.

        writer.write("NICK " + nick + "\r\n");

        writer.write("USER " + login + " 8 * : Java IRC Hacks Bot\r\n");

        writer.flush();



        // Read lines from the server until it tells us we have connected.

        String line = null;

        while ((line = reader.readLine()) != null) {

            if (line.indexOf("004") >= 0) {

                // We are now logged in.

                break;

            } else if (line.indexOf("433") >= 0) {

                kit.insertHTML(doc, doc.getLength(), "<b>" + "Username already in use!" + "</b>", 0, 0, HTML.Tag.B);
                return;
            }
        }



        // Join the channel.

        writer.write("JOIN " + channel + "\r\n");

        writer.flush();


        // Keep reading lines from the server.

        while ((line = reader.readLine()) != null) {
            
            prevLine = line;
            
            Message message = Service.parse(line);
            String command = message.getCommand();
            switch(command) {
                
                case "PING":
                    writer.write("PONG " + line.substring(5) + "\r\n");
                    writer.flush();
                    System.out.println("################# A ping was received");
                    break;
                    
                case "PRIVMSG":
                    Service.fetchMessage(line);
                    System.out.println("################# A priv msg was received");
                    break;
                    
                case "JOIN":
                    System.out.println("################# A join was received");
                    break;
                    
                case "PART":
                    Service.userLeaving(line);
                    System.out.println("################# A part msg was received");
                    break;
                    
                case "MODE":
                    System.out.println("################# A mode msg was received");
                    break;
                    
                case "NOTICE":
                    System.out.println("################# A notice msg was received");
                    break;
                
                case "TOPIC":
                    Service.fetchTopic(line);
                    System.out.println("################# A topic msg was received");
                    break;
                    
                case "KICK":
                    System.out.println("################# A kick msg was received");
                    break;
                    
                case "BAN":
                    System.out.println("################# A ban msg was received");
                    break;
                
                case "353":
                    Service.fetchUsernames(line);
                    System.out.println("################# A 353 msg was received");
                    break;
                
                default:
                    System.out.println("################# Received command: " + command);
                    ChatFrame.txtServerOutput.append(message.getMsg() + "\r\n");
                    break;
            }
        }
    }

    public static void displayMyMessage(String myMessage) throws IOException, BadLocationException {
//        kit.insertHTML(doc, doc.getLength(), "<b>" + nick + "</b>" + " :" + myMessage, 0, 0, HTML.Tag.B);
//        kit.insertHTML(doc, doc.getLength(), "<br>", 0, 0, HTML.Tag.BR);
//        writer.flush();
        String s = nick.toString();
        String sendNick = "<b><h2>" + s + "</h2></b>";
        dlModel.addElement(sendNick);
        dlModel.addElement(myMessage);
        ChatFrame.listContent.setModel(dlModel);
    }
}
