package so;

import domain.AbstractDomainObject;
import domain.SportskiObjekat;
import java.util.ArrayList;
import java.util.List;

public class SOUcitajListuSportskihObjekata extends AbstractSO {

    private List<SportskiObjekat> objekti = new ArrayList<>();

    @Override
    protected void validate(Object object) throws Exception {
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        List<AbstractDomainObject> lista = broker.getAll(new SportskiObjekat(), "");
        objekti = lista.stream().map(ado -> (SportskiObjekat) ado).toList();
    }

    public List<SportskiObjekat> getObjekti() {
        return objekti;
    }
}
