package so;

import domain.StavkaInventara;
import domain.TipOpreme;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import komunikacija.UbaciTipOpremeRequest;

public class SOUbaciTipOpreme extends AbstractSO {

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof UbaciTipOpremeRequest request)) {
            throw new Exception("Sistem ne moze da zapamti tip opreme.");
        }
        TipOpreme tipOpreme = request.getTipOpreme();
        if (tipOpreme == null) {
            throw new Exception("Sistem ne moze da zapamti tip opreme.");
        }
        if (tipOpreme.getNaziv() == null || tipOpreme.getNaziv().isBlank()
                || tipOpreme.getProizvodjac() == null || tipOpreme.getProizvodjac().isBlank()
                || tipOpreme.getOpis() == null || tipOpreme.getOpis().isBlank()
                || request.getSportskiObjekat() == null || request.getSportskiObjekat().getIdObjekat() == null
                || request.getDatumNabavke() == null || request.getStanjeOpreme() == null) {
            throw new Exception("Sistem ne moze da zapamti tip opreme.");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        UbaciTipOpremeRequest request = (UbaciTipOpremeRequest) object;
        TipOpreme tipOpreme = request.getTipOpreme();

        try (PreparedStatement ps = broker.add(tipOpreme);
                ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                tipOpreme.setIdTipa(rs.getInt(1));
            }
        }

        StavkaInventara stavkaInventara = new StavkaInventara();
        stavkaInventara.setSportskiObjekat(request.getSportskiObjekat());
        stavkaInventara.setTipOpreme(tipOpreme);
        stavkaInventara.setDatumNabavke(request.getDatumNabavke());
        stavkaInventara.setStanje(request.getStanjeOpreme());

        broker.add(stavkaInventara).close();
    }
}
