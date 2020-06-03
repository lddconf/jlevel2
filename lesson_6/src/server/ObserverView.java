package server;

public class ObserverView {
    synchronized void printMessage(String name, String msg) {
        System.out.printf( "[%s]: %s\n", name, msg );
    }
}
