package so;

import domain.AbstractDomainObject;
import domain.SportskiObjekat;
import java.util.ArrayList;
import java.util.List;

public class SOPretraziSportskiObjekat extends AbstractSO {

    private List<SportskiObjekat> objekti = new ArrayList<>();

    @Override
    protected void validate(Object object) throws Exception {
        if (object != null && !(object instanceof SportskiObjekat)) {
            throw new Exception("Sistem ne moze da nadje sportske objekte.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        SportskiObjekat kriterijum = (SportskiObjekat) object;
        String uslov = "";
        if (kriterijum != null) {
            if (kriterijum.getIdObjekat() != null) {
                uslov = "WHERE idObjekat=" + kriterijum.getIdObjekat();
            } else if (kriterijum.getNaziv() != null && !kriterijum.getNaziv().isBlank()) {
                uslov = "WHERE naziv LIKE '%" + kriterijum.getNaziv().trim() + "%'";
            } else if (kriterijum.getGrad() != null && !kriterijum.getGrad().isBlank()) {
                uslov = "WHERE grad LIKE '%" + kriterijum.getGrad().trim() + "%'";
            }
        }
        List<AbstractDomainObject> lista = broker.getAll(new SportskiObjekat(), uslov);
        objekti = lista.stream().map(ado -> (SportskiObjekat) ado).toList();
    }

    public List<SportskiObjekat> getObjekti() {
        return objekti;
    }
}
