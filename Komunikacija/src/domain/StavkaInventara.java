package domain;

import enums.StanjeOpreme;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StavkaInventara implements AbstractDomainObject {

    private SportskiObjekat sportskiObjekat;
    private TipOpreme tipOpreme;
    private LocalDate datumNabavke;
    private StanjeOpreme stanje;

    public StavkaInventara() {
    }

    public StavkaInventara(SportskiObjekat sportskiObjekat, TipOpreme tipOpreme, LocalDate datumNabavke, StanjeOpreme stanje) {
        this.sportskiObjekat = sportskiObjekat;
        this.tipOpreme = tipOpreme;
        this.datumNabavke = datumNabavke;
        this.stanje = stanje;
    }

    public SportskiObjekat getSportskiObjekat() {
        return sportskiObjekat;
    }

    public void setSportskiObjekat(SportskiObjekat sportskiObjekat) {
        this.sportskiObjekat = sportskiObjekat;
    }

    public TipOpreme getTipOpreme() {
        return tipOpreme;
    }

    public void setTipOpreme(TipOpreme tipOpreme) {
        this.tipOpreme = tipOpreme;
    }

    public LocalDate getDatumNabavke() {
        return datumNabavke;
    }

    public void setDatumNabavke(LocalDate datumNabavke) {
        this.datumNabavke = datumNabavke;
    }

    public StanjeOpreme getStanje() {
        return stanje;
    }

    public void setStanje(StanjeOpreme stanje) {
        this.stanje = stanje;
    }

    @Override
    public String vratiNazivTabele() {
        return "StavkaInventara";
    }

    @Override
    public List<AbstractDomainObject> vratiListu(ResultSet rs) throws Exception {
        List<AbstractDomainObject> lista = new ArrayList<>();
        while (rs.next()) {
            SportskiObjekat objekat = new SportskiObjekat();
            objekat.setIdObjekat(rs.getInt("idObjekat"));

            TipOpreme tip = new TipOpreme();
            tip.setIdTipa(rs.getInt("idTipa"));

            Date datum = rs.getDate("datumNabavke");
            String stanjeTekst = rs.getString("stanje");
            lista.add(new StavkaInventara(
                    objekat,
                    tip,
                    datum != null ? datum.toLocalDate() : null,
                    stanjeTekst != null ? StanjeOpreme.valueOf(stanjeTekst) : null
            ));
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "idObjekat,idTipa,datumNabavke,stanje";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return sportskiObjekat.getIdObjekat() + "," + tipOpreme.getIdTipa() + ",'" + datumNabavke + "','" + stanje + "'";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "idObjekat=" + sportskiObjekat.getIdObjekat() + " AND idTipa=" + tipOpreme.getIdTipa()
                + " AND datumNabavke='" + datumNabavke + "'";
    }

    @Override
    public AbstractDomainObject vratiObjekatRS(ResultSet rs) throws Exception {
        if (rs.next()) {
            SportskiObjekat objekat = new SportskiObjekat();
            objekat.setIdObjekat(rs.getInt("idObjekat"));

            TipOpreme tip = new TipOpreme();
            tip.setIdTipa(rs.getInt("idTipa"));

            Date datum = rs.getDate("datumNabavke");
            String stanjeTekst = rs.getString("stanje");
            return new StavkaInventara(
                    objekat,
                    tip,
                    datum != null ? datum.toLocalDate() : null,
                    stanjeTekst != null ? StanjeOpreme.valueOf(stanjeTekst) : null
            );
        }
        return null;
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "stanje='" + stanje + "'";
    }
}
