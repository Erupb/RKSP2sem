package org.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread server = new Thread(() -> new Server().start());
        server.setDaemon(true);
        server.start();

        new Client().start();
    }
}
