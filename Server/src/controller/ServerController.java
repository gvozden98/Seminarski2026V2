package controller;

import domain.KategorijaClanstva;
import domain.Korisnik;
import domain.Rezervacija;
import domain.StavkaRezervacije;
import domain.SportskiObjekat;
import domain.Trening;
import java.util.List;
import komunikacija.PretraziRezervacijuRequest;
import komunikacija.PrijavaSportskiObjekatRequest;
import komunikacija.RezervacijaPretraga;
import komunikacija.PromeniRezervacijuRequest;
import komunikacija.UbaciTipOpremeRequest;
import so.SOObrisiKorisnik;
import so.SOPretraziKorisnik;
import so.SOPretraziRezervaciju;
import so.SOPrijaviSportskiObjekat;
import so.SOPromeniKorisnik;
import so.SOPromeniRezervaciju;
import so.SOSacuvajRezervaciju;
import so.SOUbaciKorisnik;
import so.SOUbaciTipOpreme;
import so.SOUcitajListuKategorijaClanstva;
import so.SOUcitajListuKorisnika;
import so.SOUcitajListuSportskihObjekata;
import so.SOUcitajListuTreninga;
import so.SOVratiListuKorisnika;
import so.SOVratiListuRezervacija;
import so.SOVratiListuStavkiRezervacije;

public class ServerController {

    private static ServerController instance;

    private ServerController() {
    }

    public static ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }
        return instance;
    }

    public SportskiObjekat prijaviSportskiObjekat(PrijavaSportskiObjekatRequest request) throws Exception {
        SOPrijaviSportskiObjekat so = new SOPrijaviSportskiObjekat();
        so.execute(request);
        return so.getSportskiObjekat();
    }

    public Rezervacija ubaciRezervaciju(komunikacija.KreirajRezervacijuRequest request) throws Exception {
        SOSacuvajRezervaciju so = new SOSacuvajRezervaciju();
        so.execute(request);
        return so.getRezervacija();
    }

    public List<RezervacijaPretraga> pretraziRezervaciju(PretraziRezervacijuRequest kriterijum) throws Exception {
        SOPretraziRezervaciju so = new SOPretraziRezervaciju();
        so.execute(kriterijum);
        return so.getRezervacije();
    }

    public void promeniRezervaciju(PromeniRezervacijuRequest request) throws Exception {
        SOPromeniRezervaciju so = new SOPromeniRezervaciju();
        so.execute(request);
    }

    public List<StavkaRezervacije> vratiListuStavkiRezervacije(Rezervacija rezervacija) throws Exception {
        SOVratiListuStavkiRezervacije so = new SOVratiListuStavkiRezervacije();
        so.execute(rezervacija);
        return so.getStavke();
    }

    public List<SportskiObjekat> vratiListuSvihSportskihObjekata() throws Exception {
        SOUcitajListuSportskihObjekata so = new SOUcitajListuSportskihObjekata();
        so.execute(null);
        return so.getObjekti();
    }

    public List<Korisnik> vratiListuSvihKorisnika() throws Exception {
        SOUcitajListuKorisnika so = new SOUcitajListuKorisnika();
        so.execute(null);
        return so.getKorisnici();
    }

    public List<Trening> vratiListuSvihTreninga() throws Exception {
        SOUcitajListuTreninga so = new SOUcitajListuTreninga();
        so.execute(null);
        return so.getTreninzi();
    }

    public List<KategorijaClanstva> vratiListuSvihKategorijaClanstva() throws Exception {
        SOUcitajListuKategorijaClanstva so = new SOUcitajListuKategorijaClanstva();
        so.execute(null);
        return so.getKategorije();
    }

    public void ubaciKorisnik(Korisnik korisnik) throws Exception {
        SOUbaciKorisnik so = new SOUbaciKorisnik();
        so.execute(korisnik);
    }

    public void promeniKorisnik(Korisnik korisnik) throws Exception {
        SOPromeniKorisnik so = new SOPromeniKorisnik();
        so.execute(korisnik);
    }

    public List<Korisnik> pretraziKorisnik(Korisnik kriterijum) throws Exception {
        SOPretraziKorisnik so = new SOPretraziKorisnik();
        so.execute(kriterijum);
        return so.getKorisnici();
    }

    public List<Korisnik> vratiListuKorisnika(Korisnik kriterijum) throws Exception {
        SOVratiListuKorisnika so = new SOVratiListuKorisnika();
        so.execute(kriterijum);
        return so.getKorisnici();
    }

    public void obrisiKorisnik(Korisnik korisnik) throws Exception {
        SOObrisiKorisnik so = new SOObrisiKorisnik();
        so.execute(korisnik);
    }

    public void ubaciTipOpreme(UbaciTipOpremeRequest request) throws Exception {
        SOUbaciTipOpreme so = new SOUbaciTipOpreme();
        so.execute(request);
    }

    public List<Rezervacija> vratiListuRezervacija(Object kriterijum) throws Exception {
        SOVratiListuRezervacija so = new SOVratiListuRezervacija();
        so.execute(kriterijum);
        return so.getRezervacije();
    }
}
