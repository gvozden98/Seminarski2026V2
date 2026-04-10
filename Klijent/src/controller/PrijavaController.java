package controller;

import cordinator.MainCordinator;
import domain.SportskiObjekat;
import forme.PrijavaDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

public class PrijavaController {

    private final PrijavaDialog prijavaDialog;

    public PrijavaController(PrijavaDialog prijavaDialog) {
        this.prijavaDialog = prijavaDialog;
        addActionListeners();
    }

    public void otvori() {
        prijavaDialog.setVisible(true);
    }

    public SportskiObjekat prijaviSportskiObjekat(String email, String sifra) throws Exception {
        SportskiObjekat objekat = Komunikacija.getInstance().prijaviSportskiObjekat(email, sifra);
        MainCordinator.getInstance().setUlogovaniSportskiObjekat(objekat);
        return objekat;
    }

    private void addActionListeners() {
        prijavaDialog.loginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prijavi(e);
            }
        });
    }

    private void prijavi(ActionEvent e) {
        try {
            String email = prijavaDialog.getEmail();
            String sifra = prijavaDialog.getSifra();

            if (email.isBlank() || sifra.isBlank()) {
                throw new Exception("Korisnicko ime i sifra nisu ispravni.");
            }

            Komunikacija.getInstance().connect();
            prijaviSportskiObjekat(email, sifra);
            JOptionPane.showMessageDialog(prijavaDialog, "Korisnicko ime i sifra su ispravni.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            try {
                MainCordinator.getInstance().otvoriGlavnuFormu();
                prijavaDialog.dispose();
            } catch (Exception ex) {
                throw new Exception("Ne moze da se otvori glavna forma i meni.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(prijavaDialog, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            prijavaDialog.ocistiPolja();
        }
    }
}
