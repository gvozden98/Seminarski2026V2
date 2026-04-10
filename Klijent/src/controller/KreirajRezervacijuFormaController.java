package controller;

import cordinator.MainCordinator;
import domain.KategorijaClanstva;
import domain.Korisnik;
import domain.Rezervacija;
import domain.StavkaRezervacije;
import domain.SportskiObjekat;
import domain.Trening;
import enums.StatusRezervacije;
import forme.KreirajRezervacijuForma;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import komunikacija.KreirajRezervacijuRequest;
import models.StavkaRezervacijeTableModel;

public class KreirajRezervacijuFormaController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final KreirajRezervacijuForma kreirajRezervacijuForma;
    private final RezervacijaController rezervacijaController;

    public KreirajRezervacijuFormaController(KreirajRezervacijuForma kreirajRezervacijuForma) {
        this.kreirajRezervacijuForma = kreirajRezervacijuForma;
        this.rezervacijaController = new RezervacijaController();
        addActionListeners();
    }

    public void otvori() {
        pripremiFormu();
        kreirajRezervacijuForma.setVisible(true);
    }

    public KreirajRezervacijuForma getKreirajRezervacijuForma() {
        return kreirajRezervacijuForma;
    }

    private void addActionListeners() {
        kreirajRezervacijuForma.nazadAddActionListener(e -> MainCordinator.getInstance().otvoriGlavnuFormu());

        kreirajRezervacijuForma.pocetnaAddMenuListener(new MenuListener() {
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

        kreirajRezervacijuForma.kreirajAddMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                MainCordinator.getInstance().otvoriKreirajRezervacijuFormu();
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });

        kreirajRezervacijuForma.izmeniAddMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                MainCordinator.getInstance().otvoriIzmeniRezervacijuFormu();
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });

        kreirajRezervacijuForma.pretraziAddMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                MainCordinator.getInstance().otvoriPretraziRezervacijeFormu();
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });

        kreirajRezervacijuForma.treningAddItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                postaviTrajanjeZaSelektovaniTrening();
                izracunajDatumVremeZavrsetka();
            }
        });

        kreirajRezervacijuForma.datumPocetkaAddFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                izracunajDatumVremeZavrsetka();
            }
        });

        kreirajRezervacijuForma.dodajStavkuAddActionListener(e -> dodajStavku());
        kreirajRezervacijuForma.ukloniStavkuAddActionListener(e -> ukloniStavku());
        kreirajRezervacijuForma.rezervisiAddActionListener(e -> rezervisi());
    }

    private void pripremiFormu() {
        try {
            kreirajRezervacijuForma.setDatum(LocalDateTime.now().format(FORMATTER));
            List<SportskiObjekat> sportskiObjekti = rezervacijaController.vratiListuSvihSportskihObjekata();
            List<Korisnik> korisnici = rezervacijaController.vratiListuSvihKorisnika();
            List<KategorijaClanstva> kategorije = rezervacijaController.vratiListuSvihKategorijaClanstva();
            List<Trening> treninzi = rezervacijaController.vratiListuSvihTreninga();
            dopuniKorisnikeKategorijama(korisnici, kategorije);
            kreirajRezervacijuForma.setSportskiObjekti(sportskiObjekti);
            kreirajRezervacijuForma.setKorisnici(korisnici);
            kreirajRezervacijuForma.setTreninzi(treninzi);
            postaviTrajanjeZaSelektovaniTrening();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(kreirajRezervacijuForma, e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void postaviTrajanjeZaSelektovaniTrening() {
        Trening trening = kreirajRezervacijuForma.getSelektovaniTrening();
        if (trening == null || trening.getTrajanjeMin() == null) {
            kreirajRezervacijuForma.setTrajanje("");
            return;
        }
        kreirajRezervacijuForma.setTrajanje(String.valueOf(trening.getTrajanjeMin()));
    }

    private void izracunajDatumVremeZavrsetka() {
        String datumVremePocetka = kreirajRezervacijuForma.getDatumVremePocetka();
        String trajanje = kreirajRezervacijuForma.getTrajanje();

        if (datumVremePocetka.isBlank() || trajanje.isBlank()) {
            kreirajRezervacijuForma.setDatumVremeZavrsetka("");
            return;
        }

        try {
            LocalDateTime pocetak = LocalDateTime.parse(datumVremePocetka, FORMATTER);
            int trajanjeMin = Integer.parseInt(trajanje);
            LocalDateTime kraj = pocetak.plusMinutes(trajanjeMin);
            kreirajRezervacijuForma.setDatumVremeZavrsetka(kraj.format(FORMATTER));
        } catch (DateTimeParseException e) {
            kreirajRezervacijuForma.setDatumVremeZavrsetka("");
            JOptionPane.showMessageDialog(kreirajRezervacijuForma, "Datum i vreme pocetka moraju biti u formatu yyyy-MM-dd HH:mm:ss.", "Greska", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            kreirajRezervacijuForma.setDatumVremeZavrsetka("");
        }
    }

    private void dodajStavku() {
        try {
            Korisnik korisnik = kreirajRezervacijuForma.getSelektovaniKorisnik();
            Trening trening = kreirajRezervacijuForma.getSelektovaniTrening();
            String trajanjeTekst = kreirajRezervacijuForma.getTrajanje();
            String pocetakTekst = kreirajRezervacijuForma.getDatumVremePocetka();
            String krajTekst = kreirajRezervacijuForma.getDatumVremeZavrsetka();

            if (korisnik == null || trening == null) {
                throw new Exception("Sistem ne moze da doda stavku rezervacije.");
            }
            if (trajanjeTekst.isBlank() || pocetakTekst.isBlank() || krajTekst.isBlank()) {
                throw new Exception("Sistem ne moze da doda stavku rezervacije.");
            }

            int trajanjeMin = Integer.parseInt(trajanjeTekst);
            LocalDateTime pocetak = LocalDateTime.parse(pocetakTekst, FORMATTER);
            LocalDateTime kraj = LocalDateTime.parse(krajTekst, FORMATTER);

            BigDecimal cenaPoSatu = trening.getOsnovnaCenaPoSatu();
            if (cenaPoSatu == null) {
                throw new Exception("Sistem ne moze da doda stavku rezervacije.");
            }

            BigDecimal iznos = cenaPoSatu
                    .multiply(BigDecimal.valueOf(trajanjeMin))
                    .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

            BigDecimal popustIznos = BigDecimal.ZERO;
            if (korisnik.getKategorijaClanstva() != null && korisnik.getKategorijaClanstva().getPopustProcenat() != null) {
                popustIznos = BigDecimal.valueOf(korisnik.getKategorijaClanstva().getPopustProcenat());
            }

            StavkaRezervacijeTableModel model = kreirajRezervacijuForma.getStavkaRezervacijeTableModel();
            StavkaRezervacije stavka = new StavkaRezervacije();
            stavka.setRb(model.getRowCount() + 1);
            stavka.setTrening(trening);
            stavka.setCenaPoSatu(cenaPoSatu);
            stavka.setIznos(iznos);
            stavka.setPopustIznos(popustIznos);
            stavka.setDatumVremePocetka(pocetak);
            stavka.setDatumVremeKraja(kraj);
            model.dodajStavku(stavka);

            osveziUkupneVrednosti();
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(kreirajRezervacijuForma, "Datum i vreme pocetka moraju biti u formatu yyyy-MM-dd HH:mm:ss.", "Greska", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(kreirajRezervacijuForma, e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void osveziUkupneVrednosti() {
        StavkaRezervacijeTableModel model = kreirajRezervacijuForma.getStavkaRezervacijeTableModel();
        BigDecimal ukupanIznos = BigDecimal.ZERO;
        BigDecimal ukupanPopust = BigDecimal.ZERO;

        for (StavkaRezervacije stavka : model.getStavke()) {
            if (stavka.getIznos() != null) {
                ukupanIznos = ukupanIznos.add(stavka.getIznos());
                if (stavka.getPopustIznos() != null) {
                    ukupanPopust = ukupanPopust.add(
                            stavka.getIznos()
                                    .multiply(stavka.getPopustIznos())
                                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                    );
                }
            }
        }

        kreirajRezervacijuForma.setUkupanIznos(ukupanIznos.toPlainString());
        kreirajRezervacijuForma.setUkupanPopust(ukupanPopust.toPlainString());
    }

    private void ukloniStavku() {
        int selektovaniRed = kreirajRezervacijuForma.getSelektovaniRed();
        if (selektovaniRed < 0) {
            JOptionPane.showMessageDialog(kreirajRezervacijuForma, "Sistem ne moze da ukloni stavku rezervacije.", "Greska", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StavkaRezervacijeTableModel model = kreirajRezervacijuForma.getStavkaRezervacijeTableModel();
        model.obrisiStavku(selektovaniRed);
        osveziUkupneVrednosti();
    }

    private void dopuniKorisnikeKategorijama(List<Korisnik> korisnici, List<KategorijaClanstva> kategorije) {
        Map<Integer, KategorijaClanstva> kategorijePoId = kategorije.stream()
                .collect(Collectors.toMap(KategorijaClanstva::getIdKC, kategorija -> kategorija));

        for (Korisnik korisnik : korisnici) {
            if (korisnik.getKategorijaClanstva() != null && korisnik.getKategorijaClanstva().getIdKC() != null) {
                KategorijaClanstva punaKategorija = kategorijePoId.get(korisnik.getKategorijaClanstva().getIdKC());
                if (punaKategorija != null) {
                    korisnik.setKategorijaClanstva(punaKategorija);
                }
            }
        }
    }

    private void rezervisi() {
        try {
            Korisnik korisnik = kreirajRezervacijuForma.getSelektovaniKorisnik();
            SportskiObjekat sportskiObjekat = kreirajRezervacijuForma.getSelektovaniSportskiObjekat();
            StavkaRezervacijeTableModel model = kreirajRezervacijuForma.getStavkaRezervacijeTableModel();

            if (korisnik == null || sportskiObjekat == null || model.getStavke().isEmpty()) {
                throw new Exception("Sistem ne moze da zapamti Rezervaciju.");
            }

            LocalDateTime datumVreme = LocalDateTime.parse(kreirajRezervacijuForma.getDatum(), FORMATTER);
            BigDecimal ukupanIznos = izracunajUkupanIznos(model);
            BigDecimal ukupanPopust = izracunajUkupanPopust(model);

            Rezervacija rezervacija = new Rezervacija();
            rezervacija.setDatumKreiranja(datumVreme.toLocalDate());
            rezervacija.setStatusRezervacije(StatusRezervacije.KREIRANA);
            rezervacija.setUkupanIznos(ukupanIznos);
            rezervacija.setUkupanPopust(ukupanPopust);
            rezervacija.setKorisnik(korisnik);
            rezervacija.setSportskiObjekat(sportskiObjekat);

            List<StavkaRezervacije> stavke = model.getStavke().stream().map(this::kopirajStavku).collect(Collectors.toList());

            KreirajRezervacijuRequest request = new KreirajRezervacijuRequest(rezervacija, stavke);
            rezervacijaController.ubaciRezervaciju(request);
            JOptionPane.showMessageDialog(kreirajRezervacijuForma, "Sistem je zapamtio Rezervaciju.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(kreirajRezervacijuForma, "Sistem ne moze da zapamti Rezervaciju.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private StavkaRezervacije kopirajStavku(StavkaRezervacije original) {
        StavkaRezervacije stavka = new StavkaRezervacije();
        stavka.setRb(original.getRb());
        stavka.setTrening(original.getTrening());
        stavka.setIznos(original.getIznos());
        stavka.setPopustIznos(original.getPopustIznos());
        stavka.setCenaPoSatu(original.getCenaPoSatu());
        stavka.setDatumVremePocetka(original.getDatumVremePocetka());
        stavka.setDatumVremeKraja(original.getDatumVremeKraja());
        return stavka;
    }

    private BigDecimal izracunajUkupanIznos(StavkaRezervacijeTableModel model) {
        BigDecimal ukupanIznos = BigDecimal.ZERO;
        for (StavkaRezervacije stavka : model.getStavke()) {
            if (stavka.getIznos() != null) {
                ukupanIznos = ukupanIznos.add(stavka.getIznos());
            }
        }
        return ukupanIznos;
    }

    private BigDecimal izracunajUkupanPopust(StavkaRezervacijeTableModel model) {
        BigDecimal ukupanPopust = BigDecimal.ZERO;
        for (StavkaRezervacije stavka : model.getStavke()) {
            if (stavka.getIznos() != null && stavka.getPopustIznos() != null) {
                ukupanPopust = ukupanPopust.add(
                        stavka.getIznos()
                                .multiply(stavka.getPopustIznos())
                                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                );
            }
        }
        return ukupanPopust;
    }
}
