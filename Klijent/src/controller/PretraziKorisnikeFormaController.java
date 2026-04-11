package controller;

import cordinator.MainCordinator;
import domain.KategorijaClanstva;
import domain.Korisnik;
import forme.PretraziKorisnikeForma;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

public class PretraziKorisnikeFormaController {

    private final PretraziKorisnikeForma pretraziKorisnikeForma;
    private final KorisnikController korisnikController;

    public PretraziKorisnikeFormaController(PretraziKorisnikeForma pretraziKorisnikeForma) {
        this.pretraziKorisnikeForma = pretraziKorisnikeForma;
        this.korisnikController = new KorisnikController();
        addActionListeners();
    }

    public void otvori() {
        pripremiFormu();
        pretraziKorisnikeForma.setVisible(true);
    }

    private void addActionListeners() {
        pretraziKorisnikeForma.nazadAddActionListener(e -> MainCordinator.getInstance().otvoriGlavnuFormu());
        pretraziKorisnikeForma.pretraziAddActionListener(e -> pretrazi());
        pretraziKorisnikeForma.ponistiAddActionListener(e -> pretraziKorisnikeForma.resetujFiltere());
        pretraziKorisnikeForma.detaljiAddActionListener(e -> prikaziDetalje());
    }

    private void pripremiFormu() {
        try {
            List<Korisnik> korisnici = korisnikController.vratiListuKorisnika(null);
            List<KategorijaClanstva> kategorije = korisnikController.vratiListuSvihKategorijaClanstva();
            dopuniKorisnikeKategorijama(korisnici, kategorije);
            pretraziKorisnikeForma.setImena(korisnici.stream().map(Korisnik::getIme).distinct().toList());
            pretraziKorisnikeForma.setPrezimena(korisnici.stream().map(Korisnik::getPrezime).distinct().toList());
            pretraziKorisnikeForma.setEmailovi(korisnici.stream().map(Korisnik::getEmail).distinct().toList());
            pretraziKorisnikeForma.setKategorije(kategorije);
            pretraziKorisnikeForma.setKorisnici(java.util.Collections.emptyList());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pretraziKorisnikeForma, e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pretrazi() {
        try {
            List<Korisnik> korisnici = korisnikController.pretraziKorisnik(kreirajKriterijum());
            List<KategorijaClanstva> kategorije = korisnikController.vratiListuSvihKategorijaClanstva();
            dopuniKorisnikeKategorijama(korisnici, kategorije);
            if (korisnici.isEmpty()) {
                throw new Exception("Sistem ne moze da nadje korisnike po zadatim kriterijumima.");
            }
            pretraziKorisnikeForma.setKorisnici(korisnici);
            JOptionPane.showMessageDialog(pretraziKorisnikeForma, "Sistem je nasao korisnike po zadatim kriterijumima.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pretraziKorisnikeForma, e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prikaziDetalje() {
        try {
            int selektovaniIndex = pretraziKorisnikeForma.getSelektovaniIndex();
            if (selektovaniIndex < 0) {
                throw new Exception("Sistem ne moze da nadje korisnika.");
            }

            Korisnik selektovani = pretraziKorisnikeForma.getKorisnikListModel().getKorisnik(selektovaniIndex);
            Korisnik kriterijum = new Korisnik();
            kriterijum.setIdKorisnik(selektovani.getIdKorisnik());
            List<Korisnik> korisnici = korisnikController.vratiListuKorisnika(kriterijum);
            if (korisnici.isEmpty()) {
                throw new Exception("Sistem ne moze da nadje korisnika.");
            }

            List<KategorijaClanstva> kategorije = korisnikController.vratiListuSvihKategorijaClanstva();
            dopuniKorisnikeKategorijama(korisnici, kategorije);
            MainCordinator.getInstance().setSelektovaniKorisnik(korisnici.get(0));
            JOptionPane.showMessageDialog(pretraziKorisnikeForma, "Sistem je nasao korisnika.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            MainCordinator.getInstance().otvoriDetaljiKorisnikaFormu();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pretraziKorisnikeForma, e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Korisnik kreirajKriterijum() {
        Korisnik korisnik = new Korisnik();
        korisnik.setIme(pretraziKorisnikeForma.getSelektovanoIme());
        korisnik.setPrezime(pretraziKorisnikeForma.getSelektovanoPrezime());
        korisnik.setEmail(pretraziKorisnikeForma.getSelektovaniEmail());
        korisnik.setKategorijaClanstva(pretraziKorisnikeForma.getSelektovanaKategorija());
        return korisnik;
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
}
