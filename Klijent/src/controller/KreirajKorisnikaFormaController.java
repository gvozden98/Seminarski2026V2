package controller;

import cordinator.MainCordinator;
import domain.KategorijaClanstva;
import domain.Korisnik;
import forme.KreirajKorisnikaForma;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class KreirajKorisnikaFormaController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final KreirajKorisnikaForma kreirajKorisnikaForma;
    private final KorisnikController korisnikController;

    public KreirajKorisnikaFormaController(KreirajKorisnikaForma kreirajKorisnikaForma) {
        this.kreirajKorisnikaForma = kreirajKorisnikaForma;
        this.korisnikController = new KorisnikController();
        addActionListeners();
    }

    public void otvori() {
        pripremiFormu();
        kreirajKorisnikaForma.setVisible(true);
    }

    private void addActionListeners() {
        kreirajKorisnikaForma.nazadAddActionListener(e -> MainCordinator.getInstance().otvoriGlavnuFormu());
        kreirajKorisnikaForma.ubaciKorisnikaAddActionListener(e -> ubaciKorisnika());

        kreirajKorisnikaForma.pocetnaAddMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                MainCordinator.getInstance().otvoriGlavnuFormu();
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });

        kreirajKorisnikaForma.kreirajAddMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                MainCordinator.getInstance().otvoriKreirajKorisnikaFormu();
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });

        kreirajKorisnikaForma.izmeniAddMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                MainCordinator.getInstance().otvoriIzmeniKorisnikaFormu();
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });

        kreirajKorisnikaForma.pretraziAddMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                MainCordinator.getInstance().otvoriPretraziKorisnikeFormu();
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });
    }

    private void pripremiFormu() {
        try {
            List<KategorijaClanstva> kategorije = korisnikController.vratiListuSvihKategorijaClanstva();
            kreirajKorisnikaForma.setKategorije(kategorije);
            kreirajKorisnikaForma.setDatumRegistracije(LocalDate.now().format(FORMATTER));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(kreirajKorisnikaForma, e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ubaciKorisnika() {
        try {
            if (kreirajKorisnikaForma.getIme().isBlank()
                    || kreirajKorisnikaForma.getPrezime().isBlank()
                    || kreirajKorisnikaForma.getEmail().isBlank()
                    || kreirajKorisnikaForma.getTelefon().isBlank()
                    || kreirajKorisnikaForma.getDatumRegistracije().isBlank()
                    || kreirajKorisnikaForma.getSelektovanaKategorija() == null) {
                throw new Exception("Sistem ne moze da zapamti korisnika.");
            }

            Korisnik korisnik = new Korisnik();
            korisnik.setIme(kreirajKorisnikaForma.getIme());
            korisnik.setPrezime(kreirajKorisnikaForma.getPrezime());
            korisnik.setEmail(kreirajKorisnikaForma.getEmail());
            korisnik.setTelefon(kreirajKorisnikaForma.getTelefon());
            korisnik.setDatumRegistracije(LocalDate.parse(kreirajKorisnikaForma.getDatumRegistracije(), FORMATTER));
            korisnik.setKategorijaClanstva(kreirajKorisnikaForma.getSelektovanaKategorija());

            korisnikController.ubaciKorisnik(korisnik);
            JOptionPane.showMessageDialog(kreirajKorisnikaForma, "Sistem je zapamtio korisnika.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            kreirajKorisnikaForma.ocistiFormu();
            kreirajKorisnikaForma.setDatumRegistracije(LocalDate.now().format(FORMATTER));
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(kreirajKorisnikaForma, "Datum registracije mora biti u formatu yyyy-MM-dd.", "Greska", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(kreirajKorisnikaForma, "Sistem ne moze da zapamti korisnika.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }
}
