package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class IOHandler {
    private ObserverView view;
    private Socket socket;

    private DataInputStream  istream;
    private DataOutputStream ostream;

    public IOHandler(ObserverView view, Socket socket) {
        this.view = view;
        this.socket = socket;

        try {
            istream = new DataInputStream(socket.getInputStream());
            ostream = new DataOutputStream(socket.getOutputStream());

            new Thread(() ->{
                try {
                    while (true) {
                        String str = istream.readUTF();
                        if ( str.equals("/end") ) {
                            System.out.println("Client is now disconnected: " + socket.getInetAddress() + ":" + socket.getPort() );
                            break;
                        }
                        view.printMessage(socket.getInetAddress() + ":" + socket.getPort(), str);
                    }
                } catch (EOFException e ) {
                    System.out.println("Client is now disconnected: " + socket.getInetAddress() + ":" + socket.getPort() );
                } catch (IOException e) {
                    e.printStackTrace();
                }  finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage( String msg ) {
        try {
            ostream.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
