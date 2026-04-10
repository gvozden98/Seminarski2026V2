package so;

import domain.AbstractDomainObject;
import domain.SportskiObjekat;
import domain.TipOpreme;
import java.util.ArrayList;
import java.util.List;

public class SOVratiListuSportskihObjekata extends AbstractSO {

    private List<SportskiObjekat> objekti = new ArrayList<>();

    @Override
    protected void validate(Object object) throws Exception {
        if (object != null && !(object instanceof SportskiObjekat) && !(object instanceof TipOpreme)) {
            throw new Exception("Sistem ne moze da vrati listu sportskih objekata.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        String uslov = "";
        if (object != null) {
            if (object instanceof SportskiObjekat kriterijum) {
                if (kriterijum.getIdObjekat() != null) {
                    uslov = "WHERE idObjekat=" + kriterijum.getIdObjekat();
                } else if (kriterijum.getNaziv() != null && !kriterijum.getNaziv().isBlank()) {
                    uslov = "WHERE naziv LIKE '%" + kriterijum.getNaziv().trim() + "%'";
                } else if (kriterijum.getGrad() != null && !kriterijum.getGrad().isBlank()) {
                    uslov = "WHERE grad LIKE '%" + kriterijum.getGrad().trim() + "%'";
                }
            } else if (object instanceof TipOpreme tipOpreme && tipOpreme.getIdTipa() != null) {
                uslov = "WHERE idObjekat IN (SELECT idObjekat FROM StavkaInventara WHERE idTipa="
                        + tipOpreme.getIdTipa() + ")";
            }
        }
        List<AbstractDomainObject> lista = broker.getAll(new SportskiObjekat(), uslov);
        objekti = lista.stream().map(ado -> (SportskiObjekat) ado).toList();
    }

    public List<SportskiObjekat> getObjekti() {
        return objekti;
    }
}
