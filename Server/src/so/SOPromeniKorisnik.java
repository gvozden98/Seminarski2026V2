package so;

import domain.Korisnik;

public class SOPromeniKorisnik extends AbstractSO {

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof Korisnik korisnik) || korisnik.getIdKorisnik() == null) {
            throw new Exception("Sistem ne moze da promeni korisnika.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        broker.edit((Korisnik) object);
    }
}
