/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yeller.service;

import yeller.Main;
import yeller.Main;
import yeller.model.Message;
import yeller.gui.ChatFrame;
import static yeller.Main.dlModel;
import static yeller.Main.listModel;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultListModel;
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
        Main.writer.write("PRIVMSG " + Main.channel + " :" + myMessage + "\r\n");
        Main.writer.flush();

    }

    public static void sendPrivateMessage(String myMessage, String to) throws IOException {
        String s = to.substring(1);

        Main.writer.write("PRIVMSG " + Main.channel + " " + s + " :" + myMessage + "\r\n");
        Main.writer.flush();
    }

    public static void getOnlineUsers() throws IOException {

        Main.writer.write("NAMES #datamatiker" + "\r\n");
        Main.writer.flush();
    }

    public static void fetchUsernames(String line) {

        arr = line.split(" ");
        String temp = "";

        for (int i = 5; i < arr.length; i++) {
            temp = arr[i].toString();
            List<String> list = Arrays.asList(arr);
            listModel.addElement(temp);
            Main.mf.listPeople.setModel(listModel);
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
            Main.dlModel.addElement("<b><h2>"+userName+"</h2></b>");
            Main.dlModel.addElement(msg);
            ChatFrame.listContent.setModel(dlModel);
//            IrcClient.kit.insertHTML(IrcClient.doc, IrcClient.doc.getLength(), "<b>" + userName + " " + "</b>" + msg.substring(0, 50), 0, 0, HTML.Tag.B);
//            IrcClient.kit.insertHTML(IrcClient.doc, IrcClient.doc.getLength(), "<br>" + msg.substring(51, msg.length()), 0, 0, HTML.Tag.BR);
//            //  kit.insertHTML(doc, doc.getLength(),  "", 0, 0, null);
//            IrcClient.kit.insertHTML(IrcClient.doc, IrcClient.doc.getLength(), "<br>", 0, 0, HTML.Tag.BR);
        } else {
            ChatFrame.txtServerOutput.append(msg);
            Main.dlModel.addElement("<b><h2>"+userName+"</h2></b>");
            Main.dlModel.addElement(msg);
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
            Main.mf.lblTopic.setText(topic.substring(0, 100) + "...");
        } else {
            Main.mf.lblTopic.setText(topic);
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
        Main.mf.listPeople.setModel(listModel);
        listModel.removeElement(user);   
    }
    
    public static Message parse(String message)
    {
	String prefix = null;
	String command = new String();
	String trailing = null;
	String[] parameters = new String[0];

	int prefixEnd = -1;
	int trailingStart = message.length();
		
	if (message.startsWith(":"))
	{
		prefixEnd = message.indexOf(" ");
        	prefix = message.substring(1, prefixEnd);
	}
		
	trailingStart = message.indexOf(" :");
	if (trailingStart >= 0)
	{
		trailing = message.substring(trailingStart + 2);
	} 
	else 
	{
		trailingStart = message.length();
	}
	
	int start = prefixEnd + 1;
	int end = trailingStart;
	String[] commandAndParameters = message.substring(start, end).split(" ");
	command = commandAndParameters[0];
		
	if(commandAndParameters.length > 1)
	{
		parameters = Arrays.copyOfRange(commandAndParameters, 1, commandAndParameters.length, String[].class);
	}
		
	if (trailing != null)
	{
		String[] tmp = new String[parameters.length + 1];
                System.arraycopy(parameters, 0, tmp, 0, parameters.length);
		tmp[tmp.length - 1] = trailing;
		parameters = tmp;
	}
		
	Message m = new Message(prefix, command, parameters, message);
		
	return m;
    }   
}
