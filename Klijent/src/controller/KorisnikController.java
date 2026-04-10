package controller;

import domain.KategorijaClanstva;
import domain.Korisnik;
import java.util.List;
import komunikacija.Komunikacija;

public class KorisnikController {

    public void ubaciKorisnik(Korisnik korisnik) throws Exception {
        Komunikacija.getInstance().ubaciKorisnik(korisnik);
    }

    public void promeniKorisnik(Korisnik korisnik) throws Exception {
        Komunikacija.getInstance().promeniKorisnik(korisnik);
    }

    public List<Korisnik> pretraziKorisnik(Korisnik kriterijum) throws Exception {
        return Komunikacija.getInstance().pretraziKorisnik(kriterijum);
    }

    public List<Korisnik> vratiListuKorisnika(Korisnik kriterijum) throws Exception {
        return Komunikacija.getInstance().vratiListuKorisnika(kriterijum);
    }

    public void obrisiKorisnik(Korisnik korisnik) throws Exception {
        Komunikacija.getInstance().obrisiKorisnik(korisnik);
    }

    public List<KategorijaClanstva> vratiListuSvihKategorijaClanstva() throws Exception {
        return Komunikacija.getInstance().vratiListuSvihKategorijaClanstva();
    }
}
