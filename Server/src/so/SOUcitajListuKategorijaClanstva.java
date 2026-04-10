package so;

import domain.AbstractDomainObject;
import domain.KategorijaClanstva;
import java.util.ArrayList;
import java.util.List;

public class SOUcitajListuKategorijaClanstva extends AbstractSO {

    private List<KategorijaClanstva> kategorije = new ArrayList<>();

    @Override
    protected void validate(Object object) throws Exception {
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        List<AbstractDomainObject> lista = broker.getAll(new KategorijaClanstva(), "");
        kategorije = lista.stream().map(ado -> (KategorijaClanstva) ado).toList();
    }

    public List<KategorijaClanstva> getKategorije() {
        return kategorije;
    }
}
