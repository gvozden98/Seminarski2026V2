package so;

import domain.Rezervacija;

public class SOPromeniRezervaciju extends AbstractSO {

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof Rezervacija rezervacija) || rezervacija.getIdRezervacija() == null) {
            throw new Exception("Sistem ne moze da promeni rezervaciju.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        broker.edit((Rezervacija) object);
    }
}
