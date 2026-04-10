package so;

import domain.AbstractDomainObject;
import domain.KategorijaClanstva;
import java.util.ArrayList;
import java.util.List;

public class SOPretraziKategorijaClanstva extends AbstractSO {

    private List<KategorijaClanstva> kategorije = new ArrayList<>();

    @Override
    protected void validate(Object object) throws Exception {
        if (object != null && !(object instanceof KategorijaClanstva)) {
            throw new Exception("Sistem ne moze da nadje kategorije clanstva.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        KategorijaClanstva kriterijum = (KategorijaClanstva) object;
        String uslov = "";
        if (kriterijum != null) {
            if (kriterijum.getIdKC() != null) {
                uslov = "WHERE idKC=" + kriterijum.getIdKC();
            } else if (kriterijum.getNazivKategorije() != null && !kriterijum.getNazivKategorije().isBlank()) {
                uslov = "WHERE nazivKategorije LIKE '%" + kriterijum.getNazivKategorije().trim() + "%'";
            }
        }
        List<AbstractDomainObject> lista = broker.getAll(new KategorijaClanstva(), uslov);
        kategorije = lista.stream().map(ado -> (KategorijaClanstva) ado).toList();
    }

    public List<KategorijaClanstva> getKategorije() {
        return kategorije;
    }
}
