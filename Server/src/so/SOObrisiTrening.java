package so;

import domain.Trening;

public class SOObrisiTrening extends AbstractSO {

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof Trening trening) || trening.getIdTrening() == null) {
            throw new Exception("Sistem ne moze da obrise trening.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        broker.delete((Trening) object);
    }
}
