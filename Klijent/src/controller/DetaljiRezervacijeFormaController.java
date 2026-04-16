package controller;

import cordinator.MainCordinator;
import domain.Rezervacija;
import domain.StavkaRezervacije;
import forme.DetaljiRezervacijeForma;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;

public class DetaljiRezervacijeFormaController {

    private final DetaljiRezervacijeForma detaljiRezervacijeForma;
    private final RezervacijaController rezervacijaController;

    public DetaljiRezervacijeFormaController(DetaljiRezervacijeForma detaljiRezervacijeForma) {
        this.detaljiRezervacijeForma = detaljiRezervacijeForma;
        this.rezervacijaController = new RezervacijaController();
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

            List<StavkaRezervacije> stavke = MainCordinator.getInstance().getStavkeIzabraneRezervacije();
            if (stavke == null || stavke.isEmpty()) {
                stavke = rezervacijaController.vratiListuStavkiRezervacije(rezervacija);
                MainCordinator.getInstance().setStavkeIzabraneRezervacije(stavke != null ? stavke : Collections.emptyList());
            }

            detaljiRezervacijeForma.setRezervacija(rezervacija);
            detaljiRezervacijeForma.setKorisnik(rezervacija.getKorisnik());
            detaljiRezervacijeForma.setStavkeRezervacije(stavke != null ? stavke : Collections.emptyList());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(detaljiRezervacijeForma, e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }
}
