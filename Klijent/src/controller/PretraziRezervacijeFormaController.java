package controller;

import cordinator.MainCordinator;
import domain.Rezervacija;
import enums.StatusRezervacije;
import forme.PretraziRezervacijeForma;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.PretraziRezervacijuRequest;
import komunikacija.RezervacijaPretraga;
import models.RezervacijaTableModel;

public class PretraziRezervacijeFormaController {

    private final PretraziRezervacijeForma pretraziRezervacijeForma;
    private final RezervacijaController rezervacijaController;

    public PretraziRezervacijeFormaController(PretraziRezervacijeForma pretraziRezervacijeForma) {
        this.pretraziRezervacijeForma = pretraziRezervacijeForma;
        this.rezervacijaController = new RezervacijaController();
        addActionListeners();
    }

    public void otvori() {
        pripremiFormu();
        pretraziRezervacijeForma.setVisible(true);
    }

    private void addActionListeners() {
        pretraziRezervacijeForma.nazadAddActionListener(e -> MainCordinator.getInstance().otvoriGlavnuFormu());
        pretraziRezervacijeForma.pretraziAddActionListener(e -> pretrazi());
        pretraziRezervacijeForma.ponistiAddActionListener(e -> ponisti());
        pretraziRezervacijeForma.pogledajRezervacijuAddActionListener(e -> pogledajRezervaciju());
    }

    private void pripremiFormu() {
        try {
            pretraziRezervacijeForma.setSportskiObjekti(rezervacijaController.vratiListuSvihSportskihObjekata());
            pretraziRezervacijeForma.setKorisnici(rezervacijaController.vratiListuSvihKorisnika());
            pretraziRezervacijeForma.setTreninzi(rezervacijaController.vratiListuSvihTreninga());
            pretraziRezervacijeForma.setStatusi();
            pretraziRezervacijeForma.setRezervacije(java.util.Collections.emptyList());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pretraziRezervacijeForma, e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pretrazi() {
        try {
            PretraziRezervacijuRequest request = kreirajRequest();
            java.util.List<RezervacijaPretraga> rezervacije = rezervacijaController.pretraziRezervaciju(request);
            if (rezervacije == null || rezervacije.isEmpty()) {
                pretraziRezervacijeForma.setRezervacije(java.util.Collections.emptyList());
                JOptionPane.showMessageDialog(pretraziRezervacijeForma, "Sistem ne moze da nadje Rezervacije po zadatim kriterijumima.", "Greska", JOptionPane.ERROR_MESSAGE);
                return;
            }

            pretraziRezervacijeForma.setRezervacije(rezervacije);
            JOptionPane.showMessageDialog(pretraziRezervacijeForma, "Sistem je nasao Rezervacije po zadatim kriterijumima.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pretraziRezervacijeForma, e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ponisti() {
        try {
            pretraziRezervacijeForma.resetujFiltere();
            pretraziRezervacijeForma.setRezervacije(java.util.Collections.emptyList());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pretraziRezervacijeForma, e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pogledajRezervaciju() {
        int selektovaniRed = pretraziRezervacijeForma.getSelektovaniRed();
        if (selektovaniRed < 0) {
            JOptionPane.showMessageDialog(pretraziRezervacijeForma, "Sistem ne moze da nadje Rezervaciju.", "Greska", JOptionPane.ERROR_MESSAGE);
            return;
        }

        RezervacijaTableModel model = pretraziRezervacijeForma.getRezervacijaTableModel();
        RezervacijaPretraga rezervacijaPretraga = model.getRezervacija(selektovaniRed);

        try {
            Rezervacija kriterijum = new Rezervacija();
            kriterijum.setIdRezervacija(rezervacijaPretraga.getIdRezervacija());
            List<Rezervacija> rezervacije = rezervacijaController.vratiListuRezervacija(kriterijum);
            if (rezervacije == null || rezervacije.isEmpty()) {
                JOptionPane.showMessageDialog(pretraziRezervacijeForma, "Sistem ne moze da nadje Rezervaciju.", "Greska", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Rezervacija rezervacija = rezervacije.get(0);
            MainCordinator.getInstance().setIzabranaRezervacija(rezervacija);
            JOptionPane.showMessageDialog(pretraziRezervacijeForma, "Sistem je nasao Rezervaciju.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            MainCordinator.getInstance().otvoriDetaljiRezervacijeFormu();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pretraziRezervacijeForma, e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private PretraziRezervacijuRequest kreirajRequest() {
        Rezervacija rezervacija = new Rezervacija();
        StatusRezervacije statusRezervacije = pretraziRezervacijeForma.getSelektovaniStatusRezervacije();
        rezervacija.setStatusRezervacije(statusRezervacije);

        return new PretraziRezervacijuRequest(
                rezervacija,
                pretraziRezervacijeForma.getSelektovaniSportskiObjekat(),
                pretraziRezervacijeForma.getSelektovaniKorisnik(),
                pretraziRezervacijeForma.getSelektovaniTrening()
        );
    }
}
