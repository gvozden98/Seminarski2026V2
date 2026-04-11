package domain;

import enums.StatusTreninga;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Trening implements AbstractDomainObject {

    private Integer idTrening;
    private String naziv;
    private Integer trajanjeMin;
    private BigDecimal osnovnaCenaPoSatu;
    private StatusTreninga statusTreninga;

    public Trening() {
    }

    public Trening(Integer idTrening, String naziv, Integer trajanjeMin, BigDecimal osnovnaCenaPoSatu, StatusTreninga statusTreninga) {
        this.idTrening = idTrening;
        this.naziv = naziv;
        this.trajanjeMin = trajanjeMin;
        this.osnovnaCenaPoSatu = osnovnaCenaPoSatu;
        this.statusTreninga = statusTreninga;
    }

    public Integer getIdTrening() {
        return idTrening;
    }

    public void setIdTrening(Integer idTrening) {
        this.idTrening = idTrening;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getTrajanjeMin() {
        return trajanjeMin;
    }

    public void setTrajanjeMin(Integer trajanjeMin) {
        this.trajanjeMin = trajanjeMin;
    }

    public BigDecimal getOsnovnaCenaPoSatu() {
        return osnovnaCenaPoSatu;
    }

    public void setOsnovnaCenaPoSatu(BigDecimal osnovnaCenaPoSatu) {
        this.osnovnaCenaPoSatu = osnovnaCenaPoSatu;
    }

    public StatusTreninga getStatusTreninga() {
        return statusTreninga;
    }

    public void setStatusTreninga(StatusTreninga statusTreninga) {
        this.statusTreninga = statusTreninga;
    }

    @Override
    public String vratiNazivTabele() {
        return "Trening";
    }

    @Override
    public List<AbstractDomainObject> vratiListu(ResultSet rs) throws Exception {
        List<AbstractDomainObject> lista = new ArrayList<>();
        while (rs.next()) {
            String status = rs.getString("statusTreninga");
            lista.add(new Trening(
                    rs.getInt("idTrening"),
                    rs.getString("naziv"),
                    rs.getInt("trajanjeMin"),
                    rs.getBigDecimal("osnovnaCenaPoSatu"),
                    status != null ? StatusTreninga.valueOf(status) : null
            ));
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "naziv,trajanjeMin,osnovnaCenaPoSatu,statusTreninga";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "'" + naziv + "'," + trajanjeMin + "," + osnovnaCenaPoSatu + ",'" + statusTreninga + "'";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "idTrening=" + idTrening;
    }

    @Override
    public AbstractDomainObject vratiObjekatRS(ResultSet rs) throws Exception {
        if (rs.next()) {
            String status = rs.getString("statusTreninga");
            return new Trening(
                    rs.getInt("idTrening"),
                    rs.getString("naziv"),
                    rs.getInt("trajanjeMin"),
                    rs.getBigDecimal("osnovnaCenaPoSatu"),
                    status != null ? StatusTreninga.valueOf(status) : null
            );
        }
        return null;
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "naziv='" + naziv + "',trajanjeMin=" + trajanjeMin + ",osnovnaCenaPoSatu="
                + osnovnaCenaPoSatu + ",statusTreninga='" + statusTreninga + "'";
    }

    @Override
    public String toString() {
        return naziv;
    }
}
