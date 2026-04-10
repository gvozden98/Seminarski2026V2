package so;

import domain.AbstractDomainObject;
import domain.Korisnik;
import domain.Rezervacija;
import java.util.ArrayList;
import java.util.List;

public class SONadjiRezervacijeZaKorisnika extends AbstractSO {

    private List<Rezervacija> rezervacije = new ArrayList<>();

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof Korisnik korisnik) || korisnik.getIdKorisnik() == null) {
            throw new Exception("Sistem ne moze da ucita rezervacije korisnika.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        Korisnik korisnik = (Korisnik) object;
        String uslov = "WHERE idKorisnik=" + korisnik.getIdKorisnik();
        List<AbstractDomainObject> lista = broker.getAll(new Rezervacija(), uslov);
        rezervacije = lista.stream().map(ado -> (Rezervacija) ado).toList();
    }

    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }
}
