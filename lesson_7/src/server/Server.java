package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private Logger view;
    private SimpleAuthService authService;

    private Thread mainRing;

    //IOHandler handler;
    HashMap<String, IOHandler> clients;

    public Server( int port ) {
        this.port = port;
        view = new Logger();
        authService = new SimpleAuthService();

        clients = new HashMap<>();
        mainRing = new Thread(()->{
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("Server has been started on port " + port);

                while ( true ) {
                    socket = serverSocket.accept();
                    new IOHandler(view, socket, this);
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

    public void sendMessageToAllClients(String fromNick, String message) {
            clients.forEach((name, handler)-> {
                synchronized (clients) {
                    if ( !handler.getNick().equals(fromNick)) {
                        handler.sendMessage(fromNick, message);
                    }
                }
            });
    }

    public boolean sendMessageTo(String fromNick, String toNick, String message) {
        if ( toNick == "Server") return true;
        IOHandler handler = clients.get(toNick);
        if ( handler != null ) {
            synchronized (clients) {
                handler.sendMessage(fromNick, message);
            }
            return true;
        }
        return false;
    }

    public void subscribe(Socket socket, IOHandler handler ) {
        synchronized (clients) {
            clients.put(handler.getNick(), handler);
        };
    }

    public void unsubscribe(Socket socket) {
        synchronized (clients) {
            clients.remove(socket);
        };
    }


    public String getNickNameFor( String loggin, String password ) {
        synchronized (authService) {
            return authService.getNickByLoginAndPassword( loggin, password );
        }
    }

}
