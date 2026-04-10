package so;

import domain.KategorijaClanstva;

public class SOUbaciKategorijaClanstva extends AbstractSO {

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof KategorijaClanstva kategorija) || kategorija.getIdKC() == null) {
            throw new Exception("Sistem ne moze da sacuva kategoriju clanstva.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        broker.add((KategorijaClanstva) object);
    }
}
