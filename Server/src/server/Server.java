package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import thread.ClientThread;

public class Server extends Thread {

    private final int port;
    private ServerSocket serverSocket;
    private boolean pokrenut;
    private final List<ClientThread> klijenti = new ArrayList<>();

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            pokrenut = true;
            while (pokrenut) {
                Socket socket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(socket, this);
                synchronized (klijenti) {
                    klijenti.add(clientThread);
                }
                clientThread.start();
            }
        } catch (Exception e) {
            if (pokrenut) {
                System.out.println("Greska pri pokretanju servera: " + e.getMessage());
            }
        } finally {
            zaustaviServer();
        }
    }

    public void zaustaviServer() {
        pokrenut = false;
        synchronized (klijenti) {
            for (ClientThread klijent : klijenti) {
                klijent.stopRunning();
            }
            klijenti.clear();
        }
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (Exception e) {
            System.out.println("Greska pri zatvaranju servera: " + e.getMessage());
        }
    }

    public void removeClient(ClientThread clientThread) {
        synchronized (klijenti) {
            klijenti.remove(clientThread);
        }
    }
}
