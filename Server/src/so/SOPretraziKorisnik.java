package so;

import domain.AbstractDomainObject;
import domain.Korisnik;
import java.util.ArrayList;
import java.util.List;

public class SOPretraziKorisnik extends AbstractSO {

    private List<Korisnik> korisnici = new ArrayList<>();

    @Override
    protected void validate(Object object) throws Exception {
        if (object != null && !(object instanceof Korisnik)) {
            throw new Exception("Sistem ne moze da pronadje korisnike.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        Korisnik kriterijum = (Korisnik) object;
        String uslov = "";
        if (kriterijum != null) {
            if (kriterijum.getIdKorisnik() != null) {
                uslov = "WHERE idKorisnik=" + kriterijum.getIdKorisnik();
            } else if (kriterijum.getEmail() != null && !kriterijum.getEmail().isBlank()) {
                uslov = "WHERE email LIKE '%" + kriterijum.getEmail().trim() + "%'";
            } else if (kriterijum.getPrezime() != null && !kriterijum.getPrezime().isBlank()) {
                uslov = "WHERE prezime LIKE '%" + kriterijum.getPrezime().trim() + "%'";
            } else if (kriterijum.getKategorijaClanstva() != null && kriterijum.getKategorijaClanstva().getIdKC() != null) {
                uslov = "WHERE idKC=" + kriterijum.getKategorijaClanstva().getIdKC();
            }
        }
        List<AbstractDomainObject> lista = broker.getAll(new Korisnik(), uslov);
        korisnici = lista.stream().map(ado -> (Korisnik) ado).toList();
    }

    public List<Korisnik> getKorisnici() {
        return korisnici;
    }
}
