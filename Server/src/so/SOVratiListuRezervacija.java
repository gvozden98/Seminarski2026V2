package so;

import domain.AbstractDomainObject;
import domain.Korisnik;
import domain.Rezervacija;
import domain.SportskiObjekat;
import domain.StavkaRezervacije;
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
        boolean ucitajDetalje = false;
        if (object != null) {
            if (object instanceof Rezervacija rezervacija && rezervacija.getIdRezervacija() != null) {
                uslov = "WHERE idRezervacija=" + rezervacija.getIdRezervacija();
                ucitajDetalje = true;
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

        if (ucitajDetalje) {
            for (Rezervacija rezervacija : rezervacije) {
                ucitajDetaljeRezervacije(rezervacija);
            }
        }
    }

    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }

    private void ucitajDetaljeRezervacije(Rezervacija rezervacija) throws Exception {
        if (rezervacija.getKorisnik() != null && rezervacija.getKorisnik().getIdKorisnik() != null) {
            rezervacija.setKorisnik((Korisnik) broker.get(new Korisnik(), "WHERE idKorisnik=" + rezervacija.getKorisnik().getIdKorisnik()));
        }

        if (rezervacija.getSportskiObjekat() != null && rezervacija.getSportskiObjekat().getIdObjekat() != null) {
            rezervacija.setSportskiObjekat((SportskiObjekat) broker.get(new SportskiObjekat(), "WHERE idObjekat=" + rezervacija.getSportskiObjekat().getIdObjekat()));
        }

        List<AbstractDomainObject> stavkeAdo = broker.getAll(new StavkaRezervacije(), "WHERE idRezervacija=" + rezervacija.getIdRezervacija() + " ORDER BY rb ASC");
        List<StavkaRezervacije> stavke = new ArrayList<>();
        for (AbstractDomainObject ado : stavkeAdo) {
            StavkaRezervacije stavka = (StavkaRezervacije) ado;
            if (stavka.getTrening() != null && stavka.getTrening().getIdTrening() != null) {
                stavka.setTrening((Trening) broker.get(new Trening(), "WHERE idTrening=" + stavka.getTrening().getIdTrening()));
            }
            stavke.add(stavka);
        }
        rezervacija.setStavkeRezervacije(stavke);
    }
}
