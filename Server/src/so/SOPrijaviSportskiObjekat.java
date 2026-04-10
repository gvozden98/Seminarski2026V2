package so;

import domain.SportskiObjekat;
import komunikacija.PrijavaSportskiObjekatRequest;

public class SOPrijaviSportskiObjekat extends AbstractSO {

    private SportskiObjekat sportskiObjekat;

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof PrijavaSportskiObjekatRequest request)
                || request.getEmail() == null || request.getEmail().isBlank()
                || request.getSifra() == null || request.getSifra().isBlank()) {
            throw new Exception("Korisnicko ime i sifra nisu ispravni.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        PrijavaSportskiObjekatRequest request = (PrijavaSportskiObjekatRequest) object;
        String uslov = "WHERE email='" + request.getEmail().trim() + "' AND sifra='" + request.getSifra().trim() + "'";
        sportskiObjekat = (SportskiObjekat) broker.get(new SportskiObjekat(), uslov);
        if (sportskiObjekat == null) {
            throw new Exception("Korisnicko ime i sifra nisu ispravni.");
        }
    }

    public SportskiObjekat getSportskiObjekat() {
        return sportskiObjekat;
    }
}
