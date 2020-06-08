package smartchart;

import server.Loggable;

import java.net.ConnectException;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ClientIOHandler {
    private Loggable view;
    private Socket socket;
    private boolean authenticated = false;
    private String nick;
    final String IP_ADDRESS = "localhost";
    final int PORT = 10000;

    private Controller controller;
    private DataInputStream  istream;
    private DataOutputStream ostream;

    public String getNick() {
        return nick;
    }

    public ClientIOHandler(Loggable view, Controller controller) {
        this.view = view;
        this.controller = controller;
        try {
            socket = new Socket(IP_ADDRESS, PORT);;
            istream = new DataInputStream(socket.getInputStream());
            ostream = new DataOutputStream(socket.getOutputStream());

            Thread t = new Thread(() ->{
                try {
                    //Auth loop
                    while (true) {
                        String str = istream.readUTF();
                        if ( str.startsWith("/authOk ")) {
                            String[] tokens = str.split("\\s", 2);
                            if ( tokens.length != 2 ) {
                                view.printMessage("", "Authentication error");
                                continue;
                            }
                            nick = tokens[1];
                            view.printMessage("", "Authentication accepted");
                            controller.setAuthenticated(true);
                            break;
                        }
                        view.printMessage("", "Authentication error");
                    }

                    while (true) {
                        String str = istream.readUTF();
                        if ( str.equals("/end") ) {
                            break;
                        }

                        if (str.startsWith("/from ")) {
                            String[] tokens = str.split("\\s", 3);
                            if ( tokens.length == 3 ) {
                                view.printMessage(tokens[1], tokens[2]);
                            }
                        }
                    }
                } catch (EOFException e ) {

                } catch (ConnectException e) {
                   view.printMessage("","Can't connect to: " + IP_ADDRESS + ":" + PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }  finally {
                    try {
                        view.printMessage("","Connection is now closed: " + socket.getInetAddress() + ":" + socket.getPort() );
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryAuthenticate(String login, String password) {
        if (( !authenticated ) && (socket != null ) && (socket.isConnected())) {
            sendMessage("/auth " + login + " " + password);
        }
    }

    public void sendMessageToUsers( String msg ) {
        sendMessage(msg);
        view.printMessage("I'm", msg);
    }

    private void sendMessage( String msg ) {
        try {
            ostream.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
