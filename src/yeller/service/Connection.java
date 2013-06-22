/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yeller.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import yeller.Main;
import static yeller.Main.writer;
import yeller.gui.ChatFrame;
import yeller.model.Message;
import yeller.model.Server;

/**
 *
 * @author steffen
 */
public class Connection {

    Server server;
    BufferedReader reader;
    String login;
    String prevLine;
    String nick;
    // Connect directly to the IRC server.
    public static BufferedWriter writer;
    private String line;

    public Connection(Server server, String nick) throws IOException {
        this.server = server;
        this.nick = nick;
        Socket socket = new Socket(server.getAddress(), 6667);
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void joinServer() throws IOException {

        writer.write("NICK " + Main.nick + "\r\n");
        writer.write("USER " + Main.login + " 8 * : Java IRC Hacks Bot\r\n");
        writer.flush();

        // Read lines from the server until it tells us we have connected.
        String jLine = null;
        while ((jLine = reader.readLine()) != null) {
            if (jLine.indexOf("004") >= 0) {
                // We are now logged in.
                joinChannel();
                break;
            } else if (jLine.indexOf("433") >= 0) {
                return;
            }
        }
    }

    public void joinChannel() throws IOException {
        writer.write("JOIN " + Main.channel.getName() + "\r\n");
        writer.flush();

        try {
            read();
        } catch (BadLocationException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void read() throws IOException, BadLocationException {
        // Keep reading lines from the server.
        while ((line = reader.readLine()) != null) {
            prevLine = line;
            Message message = Service.parse(line);
            String command = message.getCommand();
            switch (command) {

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
}
