package controller;

import domain.KategorijaClanstva;
import cordinator.MainCordinator;
import domain.Korisnik;
import domain.Rezervacija;
import domain.SportskiObjekat;
import domain.StavkaRezervacije;
import domain.Trening;
import java.util.List;
import komunikacija.Komunikacija;
import komunikacija.KreirajRezervacijuRequest;
import komunikacija.PromeniRezervacijuRequest;
import komunikacija.PretraziRezervacijuRequest;
import komunikacija.RezervacijaPretraga;

public class RezervacijaController {

    public Rezervacija ubaciRezervaciju(KreirajRezervacijuRequest request) throws Exception {
        Rezervacija rezervacija = Komunikacija.getInstance().ubaciRezervaciju(request);
        MainCordinator.getInstance().setIzabranaRezervacija(rezervacija);
        return rezervacija;
    }

    public List<RezervacijaPretraga> pretraziRezervaciju(PretraziRezervacijuRequest kriterijum) throws Exception {
        return Komunikacija.getInstance().pretraziRezervaciju(kriterijum);
    }

    public List<Rezervacija> vratiListuRezervacija(Object kriterijum) throws Exception {
        return Komunikacija.getInstance().vratiListuRezervacija(kriterijum);
    }

    public List<StavkaRezervacije> vratiListuStavkiRezervacije(Rezervacija rezervacija) throws Exception {
        return Komunikacija.getInstance().vratiListuStavkiRezervacije(rezervacija);
    }

    public void promeniRezervaciju(PromeniRezervacijuRequest request) throws Exception {
        Komunikacija.getInstance().promeniRezervaciju(request);
    }

    public List<SportskiObjekat> vratiListuSvihSportskihObjekata() throws Exception {
        return Komunikacija.getInstance().vratiListuSvihSportskihObjekata();
    }

    public List<Korisnik> vratiListuSvihKorisnika() throws Exception {
        return Komunikacija.getInstance().vratiListuSvihKorisnika();
    }

    public List<Trening> vratiListuSvihTreninga() throws Exception {
        return Komunikacija.getInstance().vratiListuSvihTreninga();
    }

    public List<KategorijaClanstva> vratiListuSvihKategorijaClanstva() throws Exception {
        return Komunikacija.getInstance().vratiListuSvihKategorijaClanstva();
    }
}
