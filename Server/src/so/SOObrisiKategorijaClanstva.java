package so;

import domain.KategorijaClanstva;

public class SOObrisiKategorijaClanstva extends AbstractSO {

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof KategorijaClanstva kategorija) || kategorija.getIdKC() == null) {
            throw new Exception("Sistem ne moze da obrise kategoriju clanstva.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        broker.delete((KategorijaClanstva) object);
    }
}
