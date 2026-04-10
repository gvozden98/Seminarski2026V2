package so;

import domain.Korisnik;

public class SOObrisiKorisnik extends AbstractSO {

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof Korisnik korisnik) || korisnik.getIdKorisnik() == null) {
            throw new Exception("Sistem ne moze da obrise korisnika.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        broker.delete((Korisnik) object);
    }
}
