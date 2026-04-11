package so;

import domain.TipOpreme;

public class SOUbaciTipOpreme extends AbstractSO {

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof TipOpreme)) {
            throw new Exception("Sistem ne moze da sacuva tip opreme.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        broker.add((TipOpreme) object).close();
    }
}
