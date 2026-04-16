package controller;

import forme.ServerskaForma;
import javax.swing.JOptionPane;
import konfiguracija.Konfiguracija;
import server.Server;

public class ServerskaFormaController {

    private final ServerskaForma serverskaForma;
    private Server server;

    public ServerskaFormaController(ServerskaForma serverskaForma) {
        this.serverskaForma = serverskaForma;
        addActionListeners();
    }

    public void otvori() {
        serverskaForma.setStatusServera("Iskljucen");
        serverskaForma.setVisible(true);
    }

    private void addActionListeners() {
        serverskaForma.pokreniServerAddActionListener(e -> pokreniServer());
        serverskaForma.zaustaviServerAddActionListener(e -> zaustaviServer());
    }

    private void pokreniServer() {
        try {
            if (server != null && server.isPokrenut()) {
                JOptionPane.showMessageDialog(serverskaForma, "Server je vec pokrenut.", "Greska", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int port = Integer.parseInt(Konfiguracija.getInstance().getProperty("port"));
            server = new Server(port);
            server.pokreniServer();
            serverskaForma.setStatusServera("Pokrenut");
            JOptionPane.showMessageDialog(serverskaForma, "Sistem je pokrenuo server.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            serverskaForma.setStatusServera("Iskljucen");
            JOptionPane.showMessageDialog(serverskaForma, "Sistem ne moze da pokrene server.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void zaustaviServer() {
        try {
            if (server == null || !server.isPokrenut()) {
                serverskaForma.setStatusServera("Iskljucen");
                JOptionPane.showMessageDialog(serverskaForma, "Server nije pokrenut.", "Greska", JOptionPane.ERROR_MESSAGE);
                return;
            }

            server.zaustaviServer();
            serverskaForma.setStatusServera("Iskljucen");
            JOptionPane.showMessageDialog(serverskaForma, "Sistem je zaustavio server.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(serverskaForma, "Sistem ne moze da zaustavi server.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }
}
