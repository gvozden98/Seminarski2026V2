package so;

import domain.TipOpreme;

public class SOPromeniTipOpreme extends AbstractSO {

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof TipOpreme tipOpreme) || tipOpreme.getIdTipa() == null) {
            throw new Exception("Sistem ne moze da promeni tip opreme.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        broker.edit((TipOpreme) object);
    }
}
