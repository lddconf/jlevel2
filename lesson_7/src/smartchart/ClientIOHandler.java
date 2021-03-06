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
                                view.printMessage("I'm", "Authentication error");
                                continue;
                            }
                            nick = tokens[1];
                            view.printMessage("I'm", "Authentication accepted");
                            controller.setAuthenticated(true);
                            break;
                        }
                        view.printMessage("I'm", "Authentication error");
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

                        if (str.startsWith("/wErr ")) {
                            String[] tokens = str.split("\\s", 4);
                            if ( tokens.length == 4 ) {
                                view.printMessage(tokens[2], tokens[3]);
                            }
                        }
                    }
                } catch (EOFException e ) {
                } catch (ConnectException e) {
                   view.printMessage("I'm","Can't connect to: " + IP_ADDRESS + ":" + PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }  finally {
                    try {
                        view.printMessage("I'm","Connection is now closed: " + socket.getInetAddress() + ":" + socket.getPort() );
                        if ( socket != null ) {
                            socket.close();
                        }
                        socket = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        } catch (IOException e) {
            view.printMessage("I'm","Can't connect to: " + IP_ADDRESS + ":" + PORT);
            socket = null;
        }
    }

    public boolean isConnected() {
        return socket != null;
    }

    public void tryAuthenticate(String login, String password) {
        if (( !authenticated ) && (socket != null ) && (socket.isConnected())) {
            sendMessage("/auth " + login + " " + password);
        }
    }

    public void sendMessageToUsers( String msg ) {
        sendMessage(msg);

        if (msg.startsWith("/w ")) {
            String[] tokens = msg.split("\\s", 3);
            if ( tokens.length == 3 ) {
                view.printMessage("I'm->"+tokens[1], tokens[2]);
                return;
            }
        }
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
