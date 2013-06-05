/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ircclient;

import static ircclient.IrcClient.dlModel;
import static ircclient.IrcClient.listModel;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTML;

/**
 *
 * @author steffen
 */
public class Service {

    static String msg;
    static String[] arr;

    public static void sendPublicMessage(String myMessage) throws IOException, BadLocationException {

        System.out.println("Message from Service! " + myMessage);
        IrcClient.writer.write("PRIVMSG " + IrcClient.channel + " :" + myMessage + "\r\n");
        IrcClient.writer.flush();

    }

    public static void sendPrivateMessage(String myMessage, String to) throws IOException {
        String s = to.substring(1);

        IrcClient.writer.write("PRIVMSG " + IrcClient.channel + " " + s + " :" + myMessage + "\r\n");
        IrcClient.writer.flush();
    }

    public static void getOnlineUsers() throws IOException {

        IrcClient.writer.write("NAMES #datamatiker" + "\r\n");
        IrcClient.writer.flush();
    }

    public static void fetchUsernames(String line) {

        arr = line.split(" ");
        String temp = "";

        for (int i = 5; i < arr.length; i++) {
            temp = arr[i].toString();
            List<String> list = Arrays.asList(arr);
            listModel.addElement(temp);
            IrcClient.mf.listPeople.setModel(listModel);
        }
    }

    public static void fetchMessage(String line) throws BadLocationException, IOException {
        String[] arr;
        arr = line.split(" ");
        msg = "";
        for (int i = 3; i < arr.length; i++) {
            msg = msg + " " + arr[i];
        }

        arr = line.split("!");
        String temp = arr[0];
        String userName = temp.substring(1);

        System.out.println("Dette er msg:-------------> " + msg);
        if (msg.length() > 1000) {
            ChatFrame.txtServerOutput.append(msg);
            IrcClient.dlModel.addElement("<b><h2>"+userName+"</h2></b>");
            IrcClient.dlModel.addElement(msg);
            ChatFrame.listContent.setModel(dlModel);
//            IrcClient.kit.insertHTML(IrcClient.doc, IrcClient.doc.getLength(), "<b>" + userName + " " + "</b>" + msg.substring(0, 50), 0, 0, HTML.Tag.B);
//            IrcClient.kit.insertHTML(IrcClient.doc, IrcClient.doc.getLength(), "<br>" + msg.substring(51, msg.length()), 0, 0, HTML.Tag.BR);
//            //  kit.insertHTML(doc, doc.getLength(),  "", 0, 0, null);
//            IrcClient.kit.insertHTML(IrcClient.doc, IrcClient.doc.getLength(), "<br>", 0, 0, HTML.Tag.BR);
        } else {
            ChatFrame.txtServerOutput.append(msg);
            IrcClient.dlModel.addElement("<b><h2>"+userName+"</h2></b>");
            IrcClient.dlModel.addElement(msg);
            ChatFrame.listContent.setModel(dlModel);
//            IrcClient.kit.insertHTML(IrcClient.doc, IrcClient.doc.getLength(), "<b>" + userName + "</b>" + msg, 0, 0, HTML.Tag.B);
//            IrcClient.kit.insertHTML(IrcClient.doc, IrcClient.doc.getLength(), "<br>", 0, 0, HTML.Tag.BR);
        }
    }

    public static void fetchTopic(String line) {
        String[] arr;
        arr = line.split(" ");
        String topic = "";

        for (int i = 5; i < arr.length; i++) {
            topic = topic + " " + arr[i];
        }
        if (arr.length > 10) {
            IrcClient.mf.lblTopic.setText(topic.substring(0, 100) + "...");
        } else {
            IrcClient.mf.lblTopic.setText(topic);
        }
    }

    public static void userLeaving(String line) {
        //fetch the leaving username
        String[] tempArr;
        tempArr = line.split("!");
        String tempStr = tempArr[0];
        String user = tempStr.substring(1);
        
        //remove the user from the array containing all online users
        List<String> list = Arrays.asList(arr);
        list.remove(user);
        arr = list.toArray(new String[list.size()]);
        IrcClient.mf.listPeople.setModel(listModel);
        listModel.removeElement(user);   
    }
}
