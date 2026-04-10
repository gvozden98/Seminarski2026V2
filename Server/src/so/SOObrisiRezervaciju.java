package so;

import domain.Rezervacija;

public class SOObrisiRezervaciju extends AbstractSO {

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof Rezervacija rezervacija) || rezervacija.getIdRezervacija() == null) {
            throw new Exception("Sistem ne moze da obrise rezervaciju.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        broker.delete((Rezervacija) object);
    }
}
