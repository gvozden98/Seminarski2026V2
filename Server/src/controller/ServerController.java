package controller;

import domain.KategorijaClanstva;
import domain.Korisnik;
import domain.Rezervacija;
import domain.StavkaRezervacije;
import domain.SportskiObjekat;
import domain.TipOpreme;
import domain.Trening;
import java.util.List;
import komunikacija.PretraziRezervacijuRequest;
import komunikacija.PrijavaSportskiObjekatRequest;
import komunikacija.RezervacijaPretraga;
import komunikacija.PromeniRezervacijuRequest;
import komunikacija.UbaciTipOpremeRequest;
import so.SOObrisiKategorijaClanstva;
import so.SOObrisiKorisnik;
import so.SOObrisiTrening;
import so.SOPretraziKategorijaClanstva;
import so.SOPretraziKorisnik;
import so.SOPretraziRezervaciju;
import so.SOPretraziSportskiObjekat;
import so.SOPretraziTipOpreme;
import so.SOPretraziTrening;
import so.SOPrijaviSportskiObjekat;
import so.SOPromeniKategorijaClanstva;
import so.SOPromeniKorisnik;
import so.SOPromeniRezervaciju;
import so.SOPromeniTipOpreme;
import so.SOPromeniTrening;
import so.SOSacuvajRezervaciju;
import so.SOUbaciKategorijaClanstva;
import so.SOUbaciKorisnik;
import so.SOUbaciTipOpreme;
import so.SOUbaciTrening;
import so.SOUcitajListuKategorijaClanstva;
import so.SOUcitajListuKorisnika;
import so.SOUcitajListuSportskihObjekata;
import so.SOUcitajListuTreninga;
import so.SOVratiListuKorisnika;
import so.SOVratiListuRezervacija;
import so.SOVratiListuStavkiRezervacije;
import so.SOVratiListuSportskihObjekata;
import so.SOVratiListuTipovaOpreme;
import so.SOVratiListuTreninga;

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

    public List<SportskiObjekat> pretraziSportskiObjekat(SportskiObjekat kriterijum) throws Exception {
        SOPretraziSportskiObjekat so = new SOPretraziSportskiObjekat();
        so.execute(kriterijum);
        return so.getObjekti();
    }

    public List<SportskiObjekat> vratiListuSportskihObjekata(Object kriterijum) throws Exception {
        SOVratiListuSportskihObjekata so = new SOVratiListuSportskihObjekata();
        so.execute(kriterijum);
        return so.getObjekti();
    }

    public void ubaciTrening(Trening trening) throws Exception {
        SOUbaciTrening so = new SOUbaciTrening();
        so.execute(trening);
    }

    public List<Trening> pretraziTrening(Trening kriterijum) throws Exception {
        SOPretraziTrening so = new SOPretraziTrening();
        so.execute(kriterijum);
        return so.getTreninzi();
    }

    public List<Trening> vratiListuTreninga(Trening kriterijum) throws Exception {
        SOVratiListuTreninga so = new SOVratiListuTreninga();
        so.execute(kriterijum);
        return so.getTreninzi();
    }

    public void promeniTrening(Trening trening) throws Exception {
        SOPromeniTrening so = new SOPromeniTrening();
        so.execute(trening);
    }

    public void obrisiTrening(Trening trening) throws Exception {
        SOObrisiTrening so = new SOObrisiTrening();
        so.execute(trening);
    }

    public void ubaciKategorijaClanstva(KategorijaClanstva kategorija) throws Exception {
        SOUbaciKategorijaClanstva so = new SOUbaciKategorijaClanstva();
        so.execute(kategorija);
    }

    public List<KategorijaClanstva> pretraziKategorijaClanstva(KategorijaClanstva kriterijum) throws Exception {
        SOPretraziKategorijaClanstva so = new SOPretraziKategorijaClanstva();
        so.execute(kriterijum);
        return so.getKategorije();
    }

    public List<KategorijaClanstva> vratiListuKategorijaClanstva(KategorijaClanstva kriterijum) throws Exception {
        SOPretraziKategorijaClanstva so = new SOPretraziKategorijaClanstva();
        so.execute(kriterijum);
        return so.getKategorije();
    }

    public void promeniKategorijaClanstva(KategorijaClanstva kategorija) throws Exception {
        SOPromeniKategorijaClanstva so = new SOPromeniKategorijaClanstva();
        so.execute(kategorija);
    }

    public void obrisiKategorijaClanstva(KategorijaClanstva kategorija) throws Exception {
        SOObrisiKategorijaClanstva so = new SOObrisiKategorijaClanstva();
        so.execute(kategorija);
    }

    public void ubaciTipOpreme(UbaciTipOpremeRequest request) throws Exception {
        SOUbaciTipOpreme so = new SOUbaciTipOpreme();
        so.execute(request);
    }

    public List<TipOpreme> pretraziTipOpreme(TipOpreme kriterijum) throws Exception {
        SOPretraziTipOpreme so = new SOPretraziTipOpreme();
        so.execute(kriterijum);
        return so.getTipovi();
    }

    public List<TipOpreme> vratiListuTipOpreme(TipOpreme kriterijum) throws Exception {
        SOVratiListuTipovaOpreme so = new SOVratiListuTipovaOpreme();
        so.execute(kriterijum);
        return so.getTipovi();
    }

    public void promeniTipOpreme(TipOpreme tipOpreme) throws Exception {
        SOPromeniTipOpreme so = new SOPromeniTipOpreme();
        so.execute(tipOpreme);
    }

    public List<Rezervacija> vratiListuRezervacija(Object kriterijum) throws Exception {
        SOVratiListuRezervacija so = new SOVratiListuRezervacija();
        so.execute(kriterijum);
        return so.getRezervacije();
    }
}
