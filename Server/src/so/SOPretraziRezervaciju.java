package so;

import domain.AbstractDomainObject;
import domain.Rezervacija;
import java.util.ArrayList;
import java.util.List;

public class SOPretraziRezervaciju extends AbstractSO {

    private List<Rezervacija> rezervacije = new ArrayList<>();

    @Override
    protected void validate(Object object) throws Exception {
        if (object != null && !(object instanceof Rezervacija)) {
            throw new Exception("Sistem ne moze da nadje rezervacije.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        Rezervacija kriterijum = (Rezervacija) object;
        String uslov = "";
        if (kriterijum != null) {
            if (kriterijum.getIdRezervacija() != null) {
                uslov = "WHERE idRezervacija=" + kriterijum.getIdRezervacija();
            } else if (kriterijum.getKorisnik() != null && kriterijum.getKorisnik().getIdKorisnik() != null) {
                uslov = "WHERE idKorisnik=" + kriterijum.getKorisnik().getIdKorisnik();
            } else if (kriterijum.getSportskiObjekat() != null && kriterijum.getSportskiObjekat().getIdObjekat() != null) {
                uslov = "WHERE idObjekat=" + kriterijum.getSportskiObjekat().getIdObjekat();
            }
        }
        List<AbstractDomainObject> lista = broker.getAll(new Rezervacija(), uslov);
        rezervacije = lista.stream().map(ado -> (Rezervacija) ado).toList();
    }

    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }
}
