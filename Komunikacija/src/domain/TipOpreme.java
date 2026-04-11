package domain;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TipOpreme implements AbstractDomainObject {

    private Integer idTipa;
    private String naziv;
    private String proizvodjac;
    private String opis;

    public TipOpreme() {
    }

    public TipOpreme(Integer idTipa, String naziv, String proizvodjac, String opis) {
        this.idTipa = idTipa;
        this.naziv = naziv;
        this.proizvodjac = proizvodjac;
        this.opis = opis;
    }

    public Integer getIdTipa() {
        return idTipa;
    }

    public void setIdTipa(Integer idTipa) {
        this.idTipa = idTipa;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    @Override
    public String vratiNazivTabele() {
        return "TipOpreme";
    }

    @Override
    public List<AbstractDomainObject> vratiListu(ResultSet rs) throws Exception {
        List<AbstractDomainObject> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(new TipOpreme(
                    rs.getInt("idTipa"),
                    rs.getString("naziv"),
                    rs.getString("proizvodjac"),
                    rs.getString("opis")
            ));
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "naziv,proizvodjac,opis";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "'" + naziv + "','" + proizvodjac + "','" + opis + "'";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "idTipa=" + idTipa;
    }

    @Override
    public AbstractDomainObject vratiObjekatRS(ResultSet rs) throws Exception {
        if (rs.next()) {
            return new TipOpreme(
                    rs.getInt("idTipa"),
                    rs.getString("naziv"),
                    rs.getString("proizvodjac"),
                    rs.getString("opis")
            );
        }
        return null;
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "naziv='" + naziv + "',proizvodjac='" + proizvodjac + "',opis='" + opis + "'";
    }

    @Override
    public String toString() {
        return naziv;
    }
}
