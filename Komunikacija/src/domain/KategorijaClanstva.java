package domain;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class KategorijaClanstva implements AbstractDomainObject {

    private Integer idKC;
    private String nazivKategorije;
    private Integer brojTreninga;
    private Integer popustProcenat;

    public KategorijaClanstva() {
    }

    public KategorijaClanstva(Integer idKC, String nazivKategorije, Integer brojTreninga, Integer popustProcenat) {
        this.idKC = idKC;
        this.nazivKategorije = nazivKategorije;
        this.brojTreninga = brojTreninga;
        this.popustProcenat = popustProcenat;
    }

    public Integer getIdKC() {
        return idKC;
    }

    public void setIdKC(Integer idKC) {
        this.idKC = idKC;
    }

    public String getNazivKategorije() {
        return nazivKategorije;
    }

    public void setNazivKategorije(String nazivKategorije) {
        this.nazivKategorije = nazivKategorije;
    }

    public Integer getBrojTreninga() {
        return brojTreninga;
    }

    public void setBrojTreninga(Integer brojTreninga) {
        this.brojTreninga = brojTreninga;
    }

    public Integer getPopustProcenat() {
        return popustProcenat;
    }

    public void setPopustProcenat(Integer popustProcenat) {
        this.popustProcenat = popustProcenat;
    }

    @Override
    public String vratiNazivTabele() {
        return "KategorijaClanstva";
    }

    @Override
    public List<AbstractDomainObject> vratiListu(ResultSet rs) throws Exception {
        List<AbstractDomainObject> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(new KategorijaClanstva(
                    rs.getInt("idKC"),
                    rs.getString("nazivKategorije"),
                    rs.getInt("brojTreninga"),
                    rs.getInt("popustProcenat")
            ));
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "nazivKategorije,brojTreninga,popustProcenat";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "'" + nazivKategorije + "'," + brojTreninga + "," + popustProcenat;
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "idKC=" + idKC;
    }

    @Override
    public AbstractDomainObject vratiObjekatRS(ResultSet rs) throws Exception {
        if (rs.next()) {
            return new KategorijaClanstva(
                    rs.getInt("idKC"),
                    rs.getString("nazivKategorije"),
                    rs.getInt("brojTreninga"),
                    rs.getInt("popustProcenat")
            );
        }
        return null;
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "nazivKategorije='" + nazivKategorije + "',brojTreninga=" + brojTreninga
                + ",popustProcenat=" + popustProcenat;
    }

    @Override
    public String toString() {
        return nazivKategorije;
    }
}
