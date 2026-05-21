package controller;

import cordinator.MainCordinator;
import domain.Rezervacija;
import domain.StavkaRezervacije;
import forme.DetaljiRezervacijeForma;
import java.util.List;
import javax.swing.JOptionPane;

public class DetaljiRezervacijeFormaController {

    private final DetaljiRezervacijeForma detaljiRezervacijeForma;

    public DetaljiRezervacijeFormaController(DetaljiRezervacijeForma detaljiRezervacijeForma) {
        this.detaljiRezervacijeForma = detaljiRezervacijeForma;
        addActionListeners();
    }

    public void otvori() {
        pripremiFormu();
        detaljiRezervacijeForma.setVisible(true);
    }

    private void addActionListeners() {
        detaljiRezervacijeForma.nazadAddActionListener(e -> detaljiRezervacijeForma.dispose());
    }

    private void pripremiFormu() {
        try {
            Rezervacija rezervacija = MainCordinator.getInstance().getIzabranaRezervacija();
            if (rezervacija == null || rezervacija.getIdRezervacija() == null) {
                throw new Exception("Sistem ne moze da nadje Rezervaciju.");
            }

            List<StavkaRezervacije> stavke = rezervacija.getStavkeRezervacije();
            if (stavke == null) {
                throw new Exception("Sistem ne moze da nadje Rezervaciju.");
            }

            detaljiRezervacijeForma.setRezervacija(rezervacija);
            detaljiRezervacijeForma.setKorisnik(rezervacija.getKorisnik());
            detaljiRezervacijeForma.setStavkeRezervacije(stavke);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(detaljiRezervacijeForma, e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }
}
