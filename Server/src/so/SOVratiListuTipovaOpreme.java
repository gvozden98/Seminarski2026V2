package so;

import domain.AbstractDomainObject;
import domain.TipOpreme;
import java.util.ArrayList;
import java.util.List;

public class SOVratiListuTipovaOpreme extends AbstractSO {

    private List<TipOpreme> tipovi = new ArrayList<>();

    @Override
    protected void validate(Object object) throws Exception {
        if (object != null && !(object instanceof TipOpreme)) {
            throw new Exception("Sistem ne moze da vrati listu tipova opreme.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        TipOpreme kriterijum = (TipOpreme) object;
        String uslov = "";
        if (kriterijum != null) {
            if (kriterijum.getIdTipa() != null) {
                uslov = "WHERE idTipa=" + kriterijum.getIdTipa();
            } else if (kriterijum.getNaziv() != null && !kriterijum.getNaziv().isBlank()) {
                uslov = "WHERE naziv LIKE '%" + kriterijum.getNaziv().trim() + "%'";
            }
        }
        List<AbstractDomainObject> lista = broker.getAll(new TipOpreme(), uslov);
        tipovi = lista.stream().map(ado -> (TipOpreme) ado).toList();
    }

    public List<TipOpreme> getTipovi() {
        return tipovi;
    }
}
