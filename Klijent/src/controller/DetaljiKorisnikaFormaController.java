package controller;

import cordinator.MainCordinator;
import domain.KategorijaClanstva;
import domain.Korisnik;
import forme.DetaljiKorisnikaForma;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JOptionPane;

public class DetaljiKorisnikaFormaController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final DetaljiKorisnikaForma detaljiKorisnikaForma;
    private final KorisnikController korisnikController;

    public DetaljiKorisnikaFormaController(DetaljiKorisnikaForma detaljiKorisnikaForma) {
        this.detaljiKorisnikaForma = detaljiKorisnikaForma;
        this.korisnikController = new KorisnikController();
        addActionListeners();
    }

    public void otvori() {
        pripremiFormu();
        detaljiKorisnikaForma.setVisible(true);
    }

    private void addActionListeners() {
        detaljiKorisnikaForma.nazadAddActionListener(e -> detaljiKorisnikaForma.dispose());
        detaljiKorisnikaForma.izmeniKorisnikaAddActionListener(e -> izmeniKorisnika());
        detaljiKorisnikaForma.obrisiKorisnikaAddActionListener(e -> obrisiKorisnika());
    }

    private void pripremiFormu() {
        try {
            Korisnik korisnik = MainCordinator.getInstance().getSelektovaniKorisnik();
            List<KategorijaClanstva> kategorije = korisnikController.vratiListuSvihKategorijaClanstva();
            detaljiKorisnikaForma.setKategorije(kategorije);
            detaljiKorisnikaForma.setKorisnik(korisnik);
            detaljiKorisnikaForma.setSelektovanaKategorija(pronadjiKategoriju(kategorije, korisnik));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(detaljiKorisnikaForma, e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private KategorijaClanstva pronadjiKategoriju(List<KategorijaClanstva> kategorije, Korisnik korisnik) {
        if (korisnik == null || korisnik.getKategorijaClanstva() == null || korisnik.getKategorijaClanstva().getIdKC() == null) {
            return null;
        }
        for (KategorijaClanstva kategorija : kategorije) {
            if (kategorija.getIdKC() != null && kategorija.getIdKC().equals(korisnik.getKategorijaClanstva().getIdKC())) {
                return kategorija;
            }
        }
        return null;
    }

    private void izmeniKorisnika() {
        try {
            Korisnik postojeci = MainCordinator.getInstance().getSelektovaniKorisnik();
            if (postojeci == null || postojeci.getIdKorisnik() == null) {
                throw new Exception("Sistem ne moze da zapamti korisnika.");
            }
            if (detaljiKorisnikaForma.getIme().isBlank()
                    || detaljiKorisnikaForma.getPrezime().isBlank()
                    || detaljiKorisnikaForma.getEmail().isBlank()
                    || detaljiKorisnikaForma.getTelefon().isBlank()
                    || detaljiKorisnikaForma.getDatumRegistracije().isBlank()
                    || detaljiKorisnikaForma.getSelektovanaKategorija() == null) {
                throw new Exception("Sistem ne moze da zapamti korisnika.");
            }

            Korisnik korisnik = new Korisnik();
            korisnik.setIdKorisnik(postojeci.getIdKorisnik());
            korisnik.setIme(detaljiKorisnikaForma.getIme());
            korisnik.setPrezime(detaljiKorisnikaForma.getPrezime());
            korisnik.setEmail(detaljiKorisnikaForma.getEmail());
            korisnik.setTelefon(detaljiKorisnikaForma.getTelefon());
            korisnik.setDatumRegistracije(LocalDate.parse(detaljiKorisnikaForma.getDatumRegistracije(), FORMATTER));
            korisnik.setKategorijaClanstva(detaljiKorisnikaForma.getSelektovanaKategorija());

            korisnikController.promeniKorisnik(korisnik);
            MainCordinator.getInstance().setSelektovaniKorisnik(korisnik);
            JOptionPane.showMessageDialog(detaljiKorisnikaForma, "Sistem je zapamtio korisnika.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            detaljiKorisnikaForma.dispose();
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(detaljiKorisnikaForma, "Datum registracije mora biti u formatu yyyy-MM-dd.", "Greska", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(detaljiKorisnikaForma, "Sistem ne moze da zapamti korisnika.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void obrisiKorisnika() {
        try {
            Korisnik korisnik = MainCordinator.getInstance().getSelektovaniKorisnik();
            if (korisnik == null || korisnik.getIdKorisnik() == null) {
                throw new Exception("Sistem ne moze da obrise korisnika.");
            }

            korisnikController.obrisiKorisnik(korisnik);
            JOptionPane.showMessageDialog(detaljiKorisnikaForma,
                    "Sistem je obrisao korisnika.",
                    "Uspeh",
                    JOptionPane.INFORMATION_MESSAGE);

            detaljiKorisnikaForma.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(detaljiKorisnikaForma,
                    "Sistem ne moze da obrise korisnika.",
                    "Greska",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
