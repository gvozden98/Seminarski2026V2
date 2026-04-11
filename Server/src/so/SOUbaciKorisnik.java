package so;

import domain.Korisnik;

public class SOUbaciKorisnik extends AbstractSO {

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof Korisnik)) {
            throw new Exception("Sistem ne moze da zapamti korisnika.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        broker.add((Korisnik) object).close();
    }
}
