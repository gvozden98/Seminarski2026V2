package main;

import cordinator.MainCordinator;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainCordinator.getInstance().otvoriPrijavaDialog();
            }
        });
    }
}
