package models;

import domain.Korisnik;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

public class KorisnikListModel extends AbstractListModel<String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private List<Korisnik> korisnici = new ArrayList<>();

    @Override
    public int getSize() {
        return korisnici.size();
    }

    @Override
    public String getElementAt(int index) {
        Korisnik korisnik = korisnici.get(index);
        String datumRegistracije = korisnik.getDatumRegistracije() == null
                ? ""
                : korisnik.getDatumRegistracije().format(FORMATTER);
        String kategorija = korisnik.getKategorijaClanstva() == null || korisnik.getKategorijaClanstva().getNazivKategorije() == null
                ? ""
                : korisnik.getKategorijaClanstva().getNazivKategorije();
        return korisnik.getIme() + " " + korisnik.getPrezime()
                + " | " + korisnik.getEmail()
                + " | " + korisnik.getTelefon()
                + " | " + datumRegistracije
                + " | " + kategorija;
    }

    public void setKorisnici(List<Korisnik> korisnici) {
        this.korisnici = korisnici;
        fireContentsChanged(this, 0, getSize());
    }

    public Korisnik getKorisnik(int index) {
        if (index < 0 || index >= korisnici.size()) {
            return null;
        }
        return korisnici.get(index);
    }
}
