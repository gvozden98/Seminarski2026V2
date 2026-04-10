package so;

import domain.AbstractDomainObject;
import domain.Trening;
import java.util.ArrayList;
import java.util.List;

public class SOUcitajListuTreninga extends AbstractSO {

    private List<Trening> treninzi = new ArrayList<>();

    @Override
    protected void validate(Object object) throws Exception {
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        List<AbstractDomainObject> lista = broker.getAll(new Trening(), "");
        treninzi = lista.stream().map(ado -> (Trening) ado).toList();
    }

    public List<Trening> getTreninzi() {
        return treninzi;
    }
}
