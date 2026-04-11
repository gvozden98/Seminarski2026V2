package so;

import domain.AbstractDomainObject;
import domain.Rezervacija;
import domain.StavkaRezervacije;
import java.util.ArrayList;
import java.util.List;
import komunikacija.PromeniRezervacijuRequest;

public class SOPromeniRezervaciju extends AbstractSO {

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof PromeniRezervacijuRequest request)
                || request.getRezervacija() == null
                || request.getRezervacija().getIdRezervacija() == null
                || request.getStavke() == null
                || request.getStavke().isEmpty()) {
            throw new Exception("Sistem ne moze da zapamti Rezervaciju.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        PromeniRezervacijuRequest request = (PromeniRezervacijuRequest) object;
        Rezervacija rezervacija = request.getRezervacija();
        broker.edit(rezervacija);

        List<AbstractDomainObject> postojecaLista = broker.getAll(new StavkaRezervacije(), "WHERE idRezervacija=" + rezervacija.getIdRezervacija());
        List<StavkaRezervacije> postojeceStavke = postojecaLista.stream().map(ado -> (StavkaRezervacije) ado).toList();
        for (StavkaRezervacije stavka : postojeceStavke) {
            broker.delete(stavka);
        }

        List<StavkaRezervacije> noveStavke = new ArrayList<>(request.getStavke());
        for (StavkaRezervacije stavka : noveStavke) {
            stavka.setRezervacija(rezervacija);
            broker.add(stavka).close();
        }
    }
}
