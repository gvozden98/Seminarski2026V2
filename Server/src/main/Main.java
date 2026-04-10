package main;

import konfiguracija.Konfiguracija;
import server.Server;

public class Main {

    public static void main(String[] args) {
        int port = Integer.parseInt(Konfiguracija.getInstance().getProperty("port"));
        Server server = new Server(port);
        server.start();
        System.out.println("Server je pokrenut na portu " + port + ".");
    }
}
