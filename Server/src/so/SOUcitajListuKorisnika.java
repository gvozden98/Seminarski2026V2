package so;

import domain.AbstractDomainObject;
import domain.Korisnik;
import java.util.ArrayList;
import java.util.List;

public class SOUcitajListuKorisnika extends AbstractSO {

    private List<Korisnik> korisnici = new ArrayList<>();

    @Override
    protected void validate(Object object) throws Exception {
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        List<AbstractDomainObject> lista = broker.getAll(new Korisnik(), "");
        korisnici = lista.stream().map(ado -> (Korisnik) ado).toList();
    }

    public List<Korisnik> getKorisnici() {
        return korisnici;
    }
}
