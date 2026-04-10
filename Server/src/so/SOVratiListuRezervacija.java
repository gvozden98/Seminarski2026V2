package so;

import domain.AbstractDomainObject;
import domain.Korisnik;
import domain.Rezervacija;
import domain.SportskiObjekat;
import domain.Trening;
import java.util.ArrayList;
import java.util.List;

public class SOVratiListuRezervacija extends AbstractSO {

    private List<Rezervacija> rezervacije = new ArrayList<>();

    @Override
    protected void validate(Object object) throws Exception {
        if (object != null
                && !(object instanceof Rezervacija)
                && !(object instanceof SportskiObjekat)
                && !(object instanceof Korisnik)
                && !(object instanceof Trening)) {
            throw new Exception("Sistem ne moze da vrati listu rezervacija.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        String uslov = "";
        if (object != null) {
            if (object instanceof Rezervacija rezervacija && rezervacija.getIdRezervacija() != null) {
                uslov = "WHERE idRezervacija=" + rezervacija.getIdRezervacija();
            } else if (object instanceof SportskiObjekat sportskiObjekat && sportskiObjekat.getIdObjekat() != null) {
                uslov = "WHERE idObjekat=" + sportskiObjekat.getIdObjekat();
            } else if (object instanceof Korisnik korisnik && korisnik.getIdKorisnik() != null) {
                uslov = "WHERE idKorisnik=" + korisnik.getIdKorisnik();
            } else if (object instanceof Trening trening && trening.getIdTrening() != null) {
                uslov = "WHERE idRezervacija IN (SELECT idRezervacija FROM StavkaRezervacije WHERE idTrening="
                        + trening.getIdTrening() + ")";
            }
        }
        List<AbstractDomainObject> lista = broker.getAll(new Rezervacija(), uslov);
        rezervacije = lista.stream().map(ado -> (Rezervacija) ado).toList();
    }

    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }
}
