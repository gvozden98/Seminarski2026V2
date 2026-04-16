package main;

import controller.ServerskaFormaController;
import forme.ServerskaForma;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ServerskaForma serverskaForma = new ServerskaForma();
                ServerskaFormaController serverskaFormaController = new ServerskaFormaController(serverskaForma);
                serverskaFormaController.otvori();
            }
        });
    }
}
