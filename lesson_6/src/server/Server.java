package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Handler;

public class Server {
    private int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private ObserverView view;

    private Thread mainRing;

    //IOHandler handler;
    HashMap<String, IOHandler> clients;

    public Server( int port ) {
        this.port = port;
        view = new ObserverView();

        clients = new HashMap<>();
        mainRing = new Thread(()->{
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("Server has been started on port " + port);

                while ( true ) {
                    socket = serverSocket.accept();
                    synchronized (clients) {
                        clients.put(socket.getInetAddress() + ":" + socket.getPort(), new IOHandler(view, socket));
                    };
                    //handler = new IOHandler(view, socket);
                    System.out.println("Client is now connected: " + socket.getInetAddress() + ":" + socket.getPort() );
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    serverSocket.close();
                } catch (IOException e ) {
                    e.printStackTrace();
                }
            }
        });
        mainRing.start();
    }

    void sendMessageToClients(String message) {
            clients.forEach((name, handler)-> {
                synchronized (clients) {
                    handler.sendMessage(message);
                }
            });
    }
}
