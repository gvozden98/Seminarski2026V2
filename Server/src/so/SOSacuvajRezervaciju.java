package so;

import domain.Rezervacija;
import domain.StavkaRezervacije;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import komunikacija.KreirajRezervacijuRequest;

public class SOSacuvajRezervaciju extends AbstractSO {

    private Rezervacija rezervacija;

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof KreirajRezervacijuRequest request)) {
            throw new Exception("Sistem ne moze da sacuva rezervaciju.");
        }
        if (request.getRezervacija() == null || request.getRezervacija().getIdRezervacija() == null) {
            throw new Exception("Rezervacija nije ispravno formirana.");
        }
        if (request.getStavke() == null || request.getStavke().isEmpty()) {
            throw new Exception("Rezervacija mora imati bar jednu stavku.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        KreirajRezervacijuRequest request = (KreirajRezervacijuRequest) object;
        rezervacija = request.getRezervacija();
        broker.add(rezervacija);

        List<StavkaRezervacije> stavke = new ArrayList<>(request.getStavke());
        for (StavkaRezervacije stavka : stavke) {
            stavka.setRezervacija(rezervacija);
            PreparedStatement ps = broker.add(stavka);
            ps.close();
        }
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }
}
