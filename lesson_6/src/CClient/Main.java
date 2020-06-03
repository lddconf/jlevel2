package CClient;

import server.ObserverView;
import server.IOHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    static final String IP_ADDRESS = "localhost";
    static final int PORT = 10000;


    public static void main(String[] args) {


        Socket socket = new Socket();
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            ObserverView view = new ObserverView();
            IOHandler handler = new IOHandler(view, socket);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String message = scanner.nextLine();
                if (message.length() != 0) {
                    handler.sendMessage(message);
                }
            }
        } catch (ConnectException e) {
            System.out.println("Can't connect to: " + IP_ADDRESS + ":" + PORT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null ) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
