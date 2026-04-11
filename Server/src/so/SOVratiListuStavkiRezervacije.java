package so;

import domain.AbstractDomainObject;
import domain.Rezervacija;
import domain.StavkaRezervacije;
import java.util.ArrayList;
import java.util.List;

public class SOVratiListuStavkiRezervacije extends AbstractSO {

    private List<StavkaRezervacije> stavke = new ArrayList<>();

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof Rezervacija rezervacija) || rezervacija.getIdRezervacija() == null) {
            throw new Exception("Sistem ne moze da nadje Stavke rezervacije.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        Rezervacija rezervacija = (Rezervacija) object;
        List<AbstractDomainObject> lista = broker.getAll(new StavkaRezervacije(), "WHERE idRezervacija=" + rezervacija.getIdRezervacija() + " ORDER BY rb ASC");
        stavke = lista.stream().map(ado -> (StavkaRezervacije) ado).toList();
        for (StavkaRezervacije stavka : stavke) {
            if (stavka.getTrening() != null && stavka.getTrening().getIdTrening() != null) {
                stavka.setTrening((domain.Trening) broker.get(new domain.Trening(), "WHERE idTrening=" + stavka.getTrening().getIdTrening()));
            }
        }
    }

    public List<StavkaRezervacije> getStavke() {
        return stavke;
    }
}
