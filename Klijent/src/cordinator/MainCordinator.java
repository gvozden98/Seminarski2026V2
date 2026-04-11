package cordinator;

import controller.GlavnaFormaController;
import controller.DetaljiKorisnikaFormaController;
import controller.IzmeniRezervacijuFormaController;
import controller.KreirajKorisnikaFormaController;
import controller.KreirajTipOpremeFormaController;
import controller.KreirajRezervacijuFormaController;
import controller.PretraziRezervacijeFormaController;
import controller.PretraziKorisnikeFormaController;
import controller.PrijavaController;
import domain.Korisnik;
import domain.Rezervacija;
import domain.SportskiObjekat;
import domain.StavkaRezervacije;
import domain.Trening;
import forme.DetaljiKorisnikaForma;
import forme.GlavnaForma;
import forme.IzmeniRezervacijuForma;
import forme.KreirajKorisnikaForma;
import forme.KreirajTipOpremeForma;
import forme.KreirajRezervacijuForma;
import forme.PretraziKorisnikeForma;
import forme.PretraziRezervacijeForma;
import forme.PrijavaDialog;
import java.util.ArrayList;
import java.util.List;

public class MainCordinator {

    private static MainCordinator instance;

    private SportskiObjekat ulogovaniSportskiObjekat;
    private Korisnik selektovaniKorisnik;
    private SportskiObjekat izabraniObjekat;
    private Trening izabraniTrening;
    private Rezervacija izabranaRezervacija;
    private List<StavkaRezervacije> stavkeIzabraneRezervacije = new ArrayList<>();
    private boolean izmenaRezervacije;
    private GlavnaForma glavnaForma;
    private PrijavaController prijavaController;
    private GlavnaFormaController glavnaFormaController;
    private KreirajRezervacijuForma kreirajRezervacijuForma;
    private KreirajRezervacijuFormaController kreirajRezervacijuFormaController;
    private IzmeniRezervacijuForma izmeniRezervacijuForma;
    private IzmeniRezervacijuFormaController izmeniRezervacijuFormaController;
    private PretraziRezervacijeForma pretraziRezervacijeForma;
    private PretraziRezervacijeFormaController pretraziRezervacijeFormaController;
    private KreirajKorisnikaForma kreirajKorisnikaForma;
    private KreirajKorisnikaFormaController kreirajKorisnikaFormaController;
    private KreirajTipOpremeForma kreirajTipOpremeForma;
    private KreirajTipOpremeFormaController kreirajTipOpremeFormaController;
    private PretraziKorisnikeForma pretraziKorisnikeForma;
    private PretraziKorisnikeFormaController pretraziKorisnikeFormaController;
    private DetaljiKorisnikaForma detaljiKorisnikaForma;
    private DetaljiKorisnikaFormaController detaljiKorisnikaFormaController;

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

    public List<StavkaRezervacije> getStavkeIzabraneRezervacije() {
        return stavkeIzabraneRezervacije;
    }

    public void setStavkeIzabraneRezervacije(List<StavkaRezervacije> stavkeIzabraneRezervacije) {
        this.stavkeIzabraneRezervacije = stavkeIzabraneRezervacije;
    }

    public boolean isIzmenaRezervacije() {
        return izmenaRezervacije;
    }

    public void setIzmenaRezervacije(boolean izmenaRezervacije) {
        this.izmenaRezervacije = izmenaRezervacije;
    }

    public void zapocniKreiranjeRezervacije() {
        izmenaRezervacije = false;
        izabranaRezervacija = null;
        stavkeIzabraneRezervacije = new ArrayList<>();
        otvoriKreirajRezervacijuFormu();
    }

    public void otvoriPrijavaDialog() {
        prijavaController = new PrijavaController(new PrijavaDialog(null, true));
        prijavaController.otvori();
    }

    public void otvoriGlavnuFormu() {
        zatvoriRezervacijaForme();
        zatvoriKorisnikForme();
        zatvoriOpremaForme();
        glavnaForma = new GlavnaForma();
        glavnaForma.setUlogovaniSportskiObjekat(ulogovaniSportskiObjekat);
        glavnaFormaController = new GlavnaFormaController(glavnaForma);
        glavnaFormaController.otvori();
    }

    public void otvoriKreirajRezervacijuFormu() {
        zatvoriGlavnuFormu();
        zatvoriRezervacijaForme();
        zatvoriKorisnikForme();
        zatvoriOpremaForme();
        kreirajRezervacijuForma = new KreirajRezervacijuForma();
        kreirajRezervacijuFormaController = new KreirajRezervacijuFormaController(kreirajRezervacijuForma);
        kreirajRezervacijuFormaController.otvori();
    }

    public void otvoriIzmeniRezervacijuFormu() {
        zatvoriGlavnuFormu();
        zatvoriRezervacijaForme();
        zatvoriKorisnikForme();
        zatvoriOpremaForme();
        izmeniRezervacijuForma = new IzmeniRezervacijuForma();
        izmeniRezervacijuFormaController = new IzmeniRezervacijuFormaController(izmeniRezervacijuForma);
        izmeniRezervacijuFormaController.otvori();
    }

    public void otvoriPretraziRezervacijeFormu() {
        zatvoriGlavnuFormu();
        zatvoriRezervacijaForme();
        zatvoriKorisnikForme();
        zatvoriOpremaForme();
        pretraziRezervacijeForma = new PretraziRezervacijeForma();
        pretraziRezervacijeFormaController = new PretraziRezervacijeFormaController(pretraziRezervacijeForma);
        pretraziRezervacijeFormaController.otvori();
    }

    public void otvoriKreirajKorisnikaFormu() {
        zatvoriGlavnuFormu();
        zatvoriRezervacijaForme();
        zatvoriKorisnikForme();
        zatvoriOpremaForme();
        kreirajKorisnikaForma = new KreirajKorisnikaForma();
        kreirajKorisnikaFormaController = new KreirajKorisnikaFormaController(kreirajKorisnikaForma);
        kreirajKorisnikaFormaController.otvori();
    }

    public void otvoriPretraziKorisnikeFormu() {
        zatvoriGlavnuFormu();
        zatvoriRezervacijaForme();
        zatvoriKorisnikForme();
        zatvoriOpremaForme();
        pretraziKorisnikeForma = new PretraziKorisnikeForma();
        pretraziKorisnikeFormaController = new PretraziKorisnikeFormaController(pretraziKorisnikeForma);
        pretraziKorisnikeFormaController.otvori();
    }

    public void otvoriKreirajTipOpremeFormu() {
        zatvoriGlavnuFormu();
        zatvoriRezervacijaForme();
        zatvoriKorisnikForme();
        zatvoriOpremaForme();
        kreirajTipOpremeForma = new KreirajTipOpremeForma();
        kreirajTipOpremeFormaController = new KreirajTipOpremeFormaController(kreirajTipOpremeForma);
        kreirajTipOpremeFormaController.otvori();
    }

    public void otvoriDetaljiKorisnikaFormu() {
        java.awt.Frame parent = pretraziKorisnikeForma != null ? pretraziKorisnikeForma : glavnaForma;
        detaljiKorisnikaForma = new DetaljiKorisnikaForma(parent, true);
        detaljiKorisnikaFormaController = new DetaljiKorisnikaFormaController(detaljiKorisnikaForma);
        detaljiKorisnikaFormaController.otvori();
    }

    private void zatvoriGlavnuFormu() {
        if (glavnaForma != null) {
            glavnaForma.dispose();
            glavnaForma = null;
        }
    }

    private void zatvoriRezervacijaForme() {
        if (kreirajRezervacijuForma != null) {
            kreirajRezervacijuForma.dispose();
            kreirajRezervacijuForma = null;
            kreirajRezervacijuFormaController = null;
        }
        if (izmeniRezervacijuForma != null) {
            izmeniRezervacijuForma.dispose();
            izmeniRezervacijuForma = null;
            izmeniRezervacijuFormaController = null;
        }
        if (pretraziRezervacijeForma != null) {
            pretraziRezervacijeForma.dispose();
            pretraziRezervacijeForma = null;
            pretraziRezervacijeFormaController = null;
        }
    }

    private void zatvoriKorisnikForme() {
        if (kreirajKorisnikaForma != null) {
            kreirajKorisnikaForma.dispose();
            kreirajKorisnikaForma = null;
            kreirajKorisnikaFormaController = null;
        }
        if (pretraziKorisnikeForma != null) {
            pretraziKorisnikeForma.dispose();
            pretraziKorisnikeForma = null;
            pretraziKorisnikeFormaController = null;
        }
        if (detaljiKorisnikaForma != null) {
            detaljiKorisnikaForma.dispose();
            detaljiKorisnikaForma = null;
            detaljiKorisnikaFormaController = null;
        }
    }

    private void zatvoriOpremaForme() {
        if (kreirajTipOpremeForma != null) {
            kreirajTipOpremeForma.dispose();
            kreirajTipOpremeForma = null;
            kreirajTipOpremeFormaController = null;
        }
    }
}
