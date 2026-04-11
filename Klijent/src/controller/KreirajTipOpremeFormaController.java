package controller;

import cordinator.MainCordinator;
import domain.TipOpreme;
import java.time.LocalDate;
import forme.KreirajTipOpremeForma;
import javax.swing.JOptionPane;
import komunikacija.UbaciTipOpremeRequest;

public class KreirajTipOpremeFormaController {

    private final KreirajTipOpremeForma kreirajTipOpremeForma;
    private final TipOpremeController tipOpremeController;
    private LocalDate datumNabavke;

    public KreirajTipOpremeFormaController(KreirajTipOpremeForma kreirajTipOpremeForma) {
        this.kreirajTipOpremeForma = kreirajTipOpremeForma;
        this.tipOpremeController = new TipOpremeController();
        addActionListeners();
    }

    public void otvori() {
        pripremiFormu();
        kreirajTipOpremeForma.setVisible(true);
    }

    private void addActionListeners() {
        kreirajTipOpremeForma.nazadAddActionListener(e -> MainCordinator.getInstance().otvoriGlavnuFormu());
        kreirajTipOpremeForma.dodajAddActionListener(e -> ubaciTipOpreme());
    }

    private void pripremiFormu() {
        datumNabavke = LocalDate.now();
        kreirajTipOpremeForma.ocistiFormu();
        kreirajTipOpremeForma.setStanjaOpreme();
        kreirajTipOpremeForma.setStatusEnabled(true);
    }

    private void ubaciTipOpreme() {
        try {
            if (kreirajTipOpremeForma.getNaziv().isBlank()
                    || kreirajTipOpremeForma.getProizvodjac().isBlank()
                    || kreirajTipOpremeForma.getOpis().isBlank()
                    || kreirajTipOpremeForma.getStanjeOpreme() == null) {
                throw new Exception("Sistem ne moze da zapamti tip opreme.");
            }

            TipOpreme tipOpreme = new TipOpreme();
            tipOpreme.setNaziv(kreirajTipOpremeForma.getNaziv());
            tipOpreme.setProizvodjac(kreirajTipOpremeForma.getProizvodjac());
            tipOpreme.setOpis(kreirajTipOpremeForma.getOpis());

            UbaciTipOpremeRequest request = new UbaciTipOpremeRequest(
                    tipOpreme,
                    MainCordinator.getInstance().getUlogovaniSportskiObjekat(),
                    datumNabavke,
                    kreirajTipOpremeForma.getStanjeOpreme()
            );

            tipOpremeController.ubaciTipOpreme(request);
            JOptionPane.showMessageDialog(kreirajTipOpremeForma, "Sistem je zapamtio tip opreme.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            kreirajTipOpremeForma.ocistiFormu();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(kreirajTipOpremeForma, "Sistem ne moze da zapamti tip opreme.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }
}
