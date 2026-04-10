package so;

import domain.AbstractDomainObject;
import domain.Trening;
import java.util.ArrayList;
import java.util.List;

public class SOPretraziTrening extends AbstractSO {

    private List<Trening> treninzi = new ArrayList<>();

    @Override
    protected void validate(Object object) throws Exception {
        if (object != null && !(object instanceof Trening)) {
            throw new Exception("Sistem ne moze da nadje treninge.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        Trening kriterijum = (Trening) object;
        String uslov = "";
        if (kriterijum != null) {
            if (kriterijum.getIdTrening() != null) {
                uslov = "WHERE idTrening=" + kriterijum.getIdTrening();
            } else if (kriterijum.getNaziv() != null && !kriterijum.getNaziv().isBlank()) {
                uslov = "WHERE naziv LIKE '%" + kriterijum.getNaziv().trim() + "%'";
            }
        }
        List<AbstractDomainObject> lista = broker.getAll(new Trening(), uslov);
        treninzi = lista.stream().map(ado -> (Trening) ado).toList();
    }

    public List<Trening> getTreninzi() {
        return treninzi;
    }
}
