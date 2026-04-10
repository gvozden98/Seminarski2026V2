package so;

import domain.KategorijaClanstva;

public class SOPromeniKategorijaClanstva extends AbstractSO {

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof KategorijaClanstva kategorija) || kategorija.getIdKC() == null) {
            throw new Exception("Sistem ne moze da promeni kategoriju clanstva.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        broker.edit((KategorijaClanstva) object);
    }
}
