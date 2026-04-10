package cordinator;

import controller.PrijavaController;
import domain.Korisnik;
import domain.Rezervacija;
import domain.SportskiObjekat;
import domain.Trening;
import forme.GlavnaForma;
import forme.PrijavaDialog;

public class MainCordinator {

    private static MainCordinator instance;

    private SportskiObjekat ulogovaniSportskiObjekat;
    private Korisnik selektovaniKorisnik;
    private SportskiObjekat izabraniObjekat;
    private Trening izabraniTrening;
    private Rezervacija izabranaRezervacija;
    private GlavnaForma glavnaForma;
    private PrijavaController prijavaController;

    private MainCordinator() {
    }

    public static MainCordinator getInstance() {
        if (instance == null) {
            instance = new MainCordinator();
        }
        return instance;
    }

    public SportskiObjekat getUlogovaniSportskiObjekat() {
        return ulogovaniSportskiObjekat;
    }

    public void setUlogovaniSportskiObjekat(SportskiObjekat ulogovaniSportskiObjekat) {
        this.ulogovaniSportskiObjekat = ulogovaniSportskiObjekat;
    }

    public Korisnik getSelektovaniKorisnik() {
        return selektovaniKorisnik;
    }

    public void setSelektovaniKorisnik(Korisnik selektovaniKorisnik) {
        this.selektovaniKorisnik = selektovaniKorisnik;
    }

    public SportskiObjekat getIzabraniObjekat() {
        return izabraniObjekat;
    }

    public void setIzabraniObjekat(SportskiObjekat izabraniObjekat) {
        this.izabraniObjekat = izabraniObjekat;
    }

    public Trening getIzabraniTrening() {
        return izabraniTrening;
    }

    public void setIzabraniTrening(Trening izabraniTrening) {
        this.izabraniTrening = izabraniTrening;
    }

    public Rezervacija getIzabranaRezervacija() {
        return izabranaRezervacija;
    }

    public void setIzabranaRezervacija(Rezervacija izabranaRezervacija) {
        this.izabranaRezervacija = izabranaRezervacija;
    }

    public void otvoriPrijavaDialog() {
        prijavaController = new PrijavaController(new PrijavaDialog(null, true));
        prijavaController.otvori();
    }

    public void otvoriGlavnuFormu() {
        glavnaForma = new GlavnaForma();
        glavnaForma.setUlogovaniSportskiObjekat(ulogovaniSportskiObjekat);
        glavnaForma.setVisible(true);
    }
}
