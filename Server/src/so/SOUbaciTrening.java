package so;

import domain.Trening;

public class SOUbaciTrening extends AbstractSO {

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof Trening)) {
            throw new Exception("Sistem ne moze da sacuva trening.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        broker.add((Trening) object).close();
    }
}
