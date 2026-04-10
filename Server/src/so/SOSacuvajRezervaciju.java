package so;

import domain.Rezervacija;
import domain.StavkaRezervacije;
import domain.AbstractDomainObject;
import java.util.ArrayList;
import java.util.List;
import komunikacija.KreirajRezervacijuRequest;

public class SOSacuvajRezervaciju extends AbstractSO {

    private Rezervacija rezervacija;

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof KreirajRezervacijuRequest request)) {
            throw new Exception("Sistem ne moze da zapamti Rezervaciju.");
        }
        if (request.getRezervacija() == null) {
            throw new Exception("Sistem ne moze da zapamti Rezervaciju.");
        }
        if (request.getStavke() == null || request.getStavke().isEmpty()) {
            throw new Exception("Sistem ne moze da zapamti Rezervaciju.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        KreirajRezervacijuRequest request = (KreirajRezervacijuRequest) object;
        rezervacija = request.getRezervacija();
        rezervacija.setIdRezervacija(vratiNoviIdRezervacije());
        broker.add(rezervacija);

        List<StavkaRezervacije> stavke = new ArrayList<>(request.getStavke());
        for (StavkaRezervacije stavka : stavke) {
            stavka.setRezervacija(rezervacija);
            broker.add(stavka).close();
        }
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    private Integer vratiNoviIdRezervacije() throws Exception {
        List<AbstractDomainObject> lista = broker.getAll(new Rezervacija(), "");
        int maxId = 0;
        for (AbstractDomainObject ado : lista) {
            Rezervacija postojeca = (Rezervacija) ado;
            if (postojeca.getIdRezervacija() != null && postojeca.getIdRezervacija() > maxId) {
                maxId = postojeca.getIdRezervacija();
            }
        }
        return maxId + 1;
    }
}
