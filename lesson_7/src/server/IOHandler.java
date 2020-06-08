package server;

import javafx.scene.Parent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class IOHandler {
    private Loggable view;
    private Socket socket;
    private Server server;
    private String nick;
    private String connectionInfo;

    private DataInputStream  istream;
    private DataOutputStream ostream;

    public IOHandler(Loggable log, Socket socket, Server server) {
        this.view = log;
        this.socket = socket;
        this.server = server;
        this.nick = null;
        connectionInfo = socket.getInetAddress() + ":" + socket.getPort();
        try {
            istream = new DataInputStream(socket.getInputStream());
            ostream = new DataOutputStream(socket.getOutputStream());

            Thread t = new Thread(() ->{
                try {
                    //Auth Log
                    while (true) {
                        String str = istream.readUTF();
                        if ( !str.startsWith("/auth ")) {
                            log.printMessage(connectionInfo, "Authentication error");
                            sendMessage("/authErr Invalid authentication data");
                            continue;
                        }
                        String[] tokens = str.split("\\s");
                        if ( tokens.length != 3 ) {
                            log.printMessage(connectionInfo, "Authentication error");
                            sendMessage("/authErr Invalid authentication data");
                            continue;
                        }

                        synchronized (this) {
                            nick = server.getNickNameFor(tokens[1], tokens[2]);
                        }

                        if ( nick == null ) {
                            log.printMessage(connectionInfo, "Invalid login or password");
                            sendMessage("/authErr Invalid login or password");
                            continue;
                        }

                        log.printMessage(connectionInfo, "User \"" + nick + "\" " + "connected");
                        sendMessage("/authOk "+nick);
                        break;
                    }
                    server.subscribe(socket, this);

                    while (true) {
                        String str = istream.readUTF();
                        if ( str.equals("/end") ) {
                            break;
                        }

                        if (str.startsWith("/w ")) {
                            String[] tokens = str.split("\\s", 3);
                            if ( tokens.length == 3 ) {
                                if ( server.sendMessageTo(nick, tokens[1], tokens[2]) ) {
                                    log.printMessage(nick + "->" + tokens[1] , str);
                                } else {
                                    sendMessage("/wErr " + "User " + tokens[1] + " not found/connected]");
                                    log.printMessage(nick + "->" + tokens[1], "[Dest user not found/connected");
                                }
                            }
                        } else {
                            log.printMessage(nick , str);
                            server.sendMessageToAllClients(nick, str);
                        }

                    }
                } catch (EOFException e ) {

                } catch (IOException e) {
                    e.printStackTrace();
                }  finally {
                    try {
                        System.out.println("Connection is now closed: " + socket.getInetAddress() + ":" + socket.getPort() );
                        if ( server != null ) {
                            server.unsubscribe(socket);
                        }
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.setDaemon(true);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage( String from, String msg ) {
        this.sendMessage("/from " + from + " " + msg);
    }

    private void sendMessage( String msg ) {
        try {
            ostream.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized String getNick() {
        return nick;
    }
}
