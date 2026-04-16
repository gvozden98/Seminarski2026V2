package controller;

import cordinator.MainCordinator;
import domain.Korisnik;
import domain.Rezervacija;
import domain.StavkaRezervacije;
import domain.SportskiObjekat;
import domain.Trening;
import enums.StatusRezervacije;
import forme.IzmeniRezervacijuForma;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.PretraziRezervacijuRequest;
import komunikacija.RezervacijaPretraga;
import models.RezervacijaTableModel;

public class IzmeniRezervacijuFormaController {

    private final IzmeniRezervacijuForma izmeniRezervacijuForma;
    private final RezervacijaController rezervacijaController;

    public IzmeniRezervacijuFormaController(IzmeniRezervacijuForma izmeniRezervacijuForma) {
        this.izmeniRezervacijuForma = izmeniRezervacijuForma;
        this.rezervacijaController = new RezervacijaController();
        addActionListeners();
    }

    public void otvori() {
        pripremiFormu();
        izmeniRezervacijuForma.setVisible(true);
    }

    private void addActionListeners() {
        izmeniRezervacijuForma.nazadAddActionListener(e -> MainCordinator.getInstance().otvoriGlavnuFormu());
        izmeniRezervacijuForma.pretraziAddActionListener(e -> pretrazi());
        izmeniRezervacijuForma.ponistiAddActionListener(e -> ponisti());
        izmeniRezervacijuForma.izmeniRezervacijuAddActionListener(e -> izmeniRezervaciju());
    }

    private void pripremiFormu() {
        try {
            izmeniRezervacijuForma.setSportskiObjekti(rezervacijaController.vratiListuSvihSportskihObjekata());
            izmeniRezervacijuForma.setKorisnici(rezervacijaController.vratiListuSvihKorisnika());
            izmeniRezervacijuForma.setTreninzi(rezervacijaController.vratiListuSvihTreninga());
            izmeniRezervacijuForma.setStatusi();
            izmeniRezervacijuForma.setRezervacije(Collections.emptyList());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(izmeniRezervacijuForma, e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pretrazi() {
        try {
            PretraziRezervacijuRequest request = kreirajRequest();
            List<RezervacijaPretraga> rezervacije = rezervacijaController.pretraziRezervaciju(request);
            if (rezervacije == null || rezervacije.isEmpty()) {
                izmeniRezervacijuForma.setRezervacije(Collections.emptyList());
                JOptionPane.showMessageDialog(izmeniRezervacijuForma, "Sistem ne moze da nadje Rezervacije po zadatim kriterijumima.", "Greska", JOptionPane.ERROR_MESSAGE);
                return;
            }

            izmeniRezervacijuForma.setRezervacije(rezervacije);
            JOptionPane.showMessageDialog(izmeniRezervacijuForma, "Sistem je nasao Rezervacije po zadatim kriterijumima.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(izmeniRezervacijuForma, e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ponisti() {
        izmeniRezervacijuForma.resetujFiltere();
        izmeniRezervacijuForma.setRezervacije(Collections.emptyList());
    }

    private void izmeniRezervaciju() {
        Rezervacija rezervacija = ucitajSelektovanuRezervaciju();
        if (rezervacija == null) {
            return;
        }
        try {
            List<StavkaRezervacije> stavke = rezervacijaController.vratiListuStavkiRezervacije(rezervacija);
            MainCordinator.getInstance().setIzabranaRezervacija(rezervacija);
            MainCordinator.getInstance().setStavkeIzabraneRezervacije(stavke);
            MainCordinator.getInstance().setIzmenaRezervacije(true);
            MainCordinator.getInstance().otvoriKreirajRezervacijuFormu();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(izmeniRezervacijuForma, e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Rezervacija ucitajSelektovanuRezervaciju() {
        int selektovaniRed = izmeniRezervacijuForma.getSelektovaniRed();
        if (selektovaniRed < 0) {
            JOptionPane.showMessageDialog(izmeniRezervacijuForma, "Sistem ne moze da nadje Rezervaciju.", "Greska", JOptionPane.ERROR_MESSAGE);
            return null;
        } else {
            JOptionPane.showMessageDialog(izmeniRezervacijuForma, "Sistem je nasao Rezervaciju.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

        }

        RezervacijaTableModel model = izmeniRezervacijuForma.getRezervacijaTableModel();
        RezervacijaPretraga rezervacijaPretraga = model.getRezervacija(selektovaniRed);

        try {
            Rezervacija kriterijum = new Rezervacija();
            kriterijum.setIdRezervacija(rezervacijaPretraga.getIdRezervacija());
            List<Rezervacija> rezervacije = rezervacijaController.vratiListuRezervacija(kriterijum);
            if (rezervacije == null || rezervacije.isEmpty()) {
                JOptionPane.showMessageDialog(izmeniRezervacijuForma, "Sistem ne moze da nadje Rezervaciju.", "Greska", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            return rezervacije.get(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(izmeniRezervacijuForma, e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private PretraziRezervacijuRequest kreirajRequest() {
        Rezervacija rezervacija = new Rezervacija();
        rezervacija.setStatusRezervacije(izmeniRezervacijuForma.getSelektovaniStatusRezervacije());

        return new PretraziRezervacijuRequest(
                rezervacija,
                izmeniRezervacijuForma.getSelektovaniSportskiObjekat(),
                izmeniRezervacijuForma.getSelektovaniKorisnik(),
                izmeniRezervacijuForma.getSelektovaniTrening()
        );
    }
}
