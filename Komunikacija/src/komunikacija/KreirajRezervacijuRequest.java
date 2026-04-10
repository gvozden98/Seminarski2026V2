package komunikacija;

import domain.Rezervacija;
import domain.StavkaRezervacije;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KreirajRezervacijuRequest implements Serializable {

    private Rezervacija rezervacija;
    private List<StavkaRezervacije> stavke = new ArrayList<>();

    public KreirajRezervacijuRequest() {
    }

    public KreirajRezervacijuRequest(Rezervacija rezervacija, List<StavkaRezervacije> stavke) {
        this.rezervacija = rezervacija;
        this.stavke = stavke;
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public void setRezervacija(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
    }

    public List<StavkaRezervacije> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaRezervacije> stavke) {
        this.stavke = stavke;
    }
}
