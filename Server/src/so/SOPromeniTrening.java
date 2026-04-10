package so;

import domain.Trening;

public class SOPromeniTrening extends AbstractSO {

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof Trening trening) || trening.getIdTrening() == null) {
            throw new Exception("Sistem ne moze da promeni trening.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        broker.edit((Trening) object);
    }
}
