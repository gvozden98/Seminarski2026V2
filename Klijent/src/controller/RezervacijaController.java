package controller;

import cordinator.MainCordinator;
import domain.Korisnik;
import domain.Rezervacija;
import domain.SportskiObjekat;
import domain.Trening;
import java.util.List;
import komunikacija.Komunikacija;
import komunikacija.KreirajRezervacijuRequest;

public class RezervacijaController {

    public Rezervacija ubaciRezervaciju(KreirajRezervacijuRequest request) throws Exception {
        Rezervacija rezervacija = Komunikacija.getInstance().ubaciRezervaciju(request);
        MainCordinator.getInstance().setIzabranaRezervacija(rezervacija);
        return rezervacija;
    }

    public List<Rezervacija> pretraziRezervaciju(Rezervacija kriterijum) throws Exception {
        return Komunikacija.getInstance().pretraziRezervaciju(kriterijum);
    }

    public List<Rezervacija> vratiListuRezervacija(Object kriterijum) throws Exception {
        return Komunikacija.getInstance().vratiListuRezervacija(kriterijum);
    }

    public void promeniRezervaciju(Rezervacija rezervacija) throws Exception {
        Komunikacija.getInstance().promeniRezervaciju(rezervacija);
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
}
