package komunikacija;

import domain.SportskiObjekat;
import domain.TipOpreme;
import enums.StanjeOpreme;
import java.io.Serializable;
import java.time.LocalDate;

public class UbaciTipOpremeRequest implements Serializable {

    private TipOpreme tipOpreme;
    private SportskiObjekat sportskiObjekat;
    private LocalDate datumNabavke;
    private StanjeOpreme stanjeOpreme;

    public UbaciTipOpremeRequest() {
    }

    public UbaciTipOpremeRequest(TipOpreme tipOpreme, SportskiObjekat sportskiObjekat, LocalDate datumNabavke, StanjeOpreme stanjeOpreme) {
        this.tipOpreme = tipOpreme;
        this.sportskiObjekat = sportskiObjekat;
        this.datumNabavke = datumNabavke;
        this.stanjeOpreme = stanjeOpreme;
    }

    public TipOpreme getTipOpreme() {
        return tipOpreme;
    }

    public void setTipOpreme(TipOpreme tipOpreme) {
        this.tipOpreme = tipOpreme;
    }

    public SportskiObjekat getSportskiObjekat() {
        return sportskiObjekat;
    }

    public void setSportskiObjekat(SportskiObjekat sportskiObjekat) {
        this.sportskiObjekat = sportskiObjekat;
    }

    public LocalDate getDatumNabavke() {
        return datumNabavke;
    }

    public void setDatumNabavke(LocalDate datumNabavke) {
        this.datumNabavke = datumNabavke;
    }

    public StanjeOpreme getStanjeOpreme() {
        return stanjeOpreme;
    }

    public void setStanjeOpreme(StanjeOpreme stanjeOpreme) {
        this.stanjeOpreme = stanjeOpreme;
    }
}
