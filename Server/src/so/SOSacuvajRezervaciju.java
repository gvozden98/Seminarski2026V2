package so;

import domain.Rezervacija;
import domain.StavkaRezervacije;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import komunikacija.KreirajRezervacijuRequest;

public class SOSacuvajRezervaciju extends AbstractSO {

    private Rezervacija rezervacija;

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof KreirajRezervacijuRequest request)) {
            throw new Exception("Sistem ne moze da zapamti Rezervaciju.");
        }
        if (request.getRezervacija() == null) {
            throw new Exception("Sistem ne moze da zapamti Rezervaciju.");
        }
        if (request.getStavke() == null || request.getStavke().isEmpty()) {
            throw new Exception("Sistem ne moze da zapamti Rezervaciju.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        KreirajRezervacijuRequest request = (KreirajRezervacijuRequest) object;
        rezervacija = request.getRezervacija();
        sacuvajRezervaciju(rezervacija);

        List<StavkaRezervacije> stavke = new ArrayList<>(request.getStavke());
        for (StavkaRezervacije stavka : stavke) {
            stavka.setRezervacija(rezervacija);
            broker.add(stavka).close();
        }
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    private void sacuvajRezervaciju(Rezervacija rezervacija) throws Exception {
        try (PreparedStatement ps = broker.add(rezervacija);
                ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                rezervacija.setIdRezervacija(rs.getInt(1));
            }
        }
    }
}
