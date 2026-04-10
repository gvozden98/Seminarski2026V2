package domain;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StavkaRezervacije implements AbstractDomainObject {

    private Rezervacija rezervacija;
    private Integer rb;
    private Trening trening;
    private BigDecimal iznos;
    private BigDecimal popustIznos;
    private BigDecimal cenaPoSatu;
    private LocalDateTime datumVremePocetka;
    private LocalDateTime datumVremeKraja;

    public StavkaRezervacije() {
    }

    public StavkaRezervacije(Rezervacija rezervacija, Integer rb, Trening trening, BigDecimal iznos, BigDecimal popustIznos, BigDecimal cenaPoSatu, LocalDateTime datumVremePocetka, LocalDateTime datumVremeKraja) {
        this.rezervacija = rezervacija;
        this.rb = rb;
        this.trening = trening;
        this.iznos = iznos;
        this.popustIznos = popustIznos;
        this.cenaPoSatu = cenaPoSatu;
        this.datumVremePocetka = datumVremePocetka;
        this.datumVremeKraja = datumVremeKraja;
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public void setRezervacija(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
    }

    public Integer getRb() {
        return rb;
    }

    public void setRb(Integer rb) {
        this.rb = rb;
    }

    public Trening getTrening() {
        return trening;
    }

    public void setTrening(Trening trening) {
        this.trening = trening;
    }

    public BigDecimal getIznos() {
        return iznos;
    }

    public void setIznos(BigDecimal iznos) {
        this.iznos = iznos;
    }

    public BigDecimal getPopustIznos() {
        return popustIznos;
    }

    public void setPopustIznos(BigDecimal popustIznos) {
        this.popustIznos = popustIznos;
    }

    public BigDecimal getCenaPoSatu() {
        return cenaPoSatu;
    }

    public void setCenaPoSatu(BigDecimal cenaPoSatu) {
        this.cenaPoSatu = cenaPoSatu;
    }

    public LocalDateTime getDatumVremePocetka() {
        return datumVremePocetka;
    }

    public void setDatumVremePocetka(LocalDateTime datumVremePocetka) {
        this.datumVremePocetka = datumVremePocetka;
    }

    public LocalDateTime getDatumVremeKraja() {
        return datumVremeKraja;
    }

    public void setDatumVremeKraja(LocalDateTime datumVremeKraja) {
        this.datumVremeKraja = datumVremeKraja;
    }

    @Override
    public String vratiNazivTabele() {
        return "StavkaRezervacije";
    }

    @Override
    public List<AbstractDomainObject> vratiListu(ResultSet rs) throws Exception {
        List<AbstractDomainObject> lista = new ArrayList<>();
        while (rs.next()) {
            Rezervacija rezervacijaObj = new Rezervacija();
            rezervacijaObj.setIdRezervacija(rs.getInt("idRezervacija"));

            Trening treningObj = new Trening();
            treningObj.setIdTrening(rs.getInt("idTrening"));

            Timestamp pocetak = rs.getTimestamp("datumVremePocetka");
            Timestamp kraj = rs.getTimestamp("datumVremeKraja");

            lista.add(new StavkaRezervacije(
                    rezervacijaObj,
                    rs.getInt("rb"),
                    treningObj,
                    rs.getBigDecimal("iznos"),
                    rs.getBigDecimal("popustIznos"),
                    rs.getBigDecimal("cenaPoSatu"),
                    pocetak != null ? pocetak.toLocalDateTime() : null,
                    kraj != null ? kraj.toLocalDateTime() : null
            ));
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "idRezervacija,rb,idTrening,iznos,popustIznos,cenaPoSatu,datumVremePocetka,datumVremeKraja";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return rezervacija.getIdRezervacija() + "," + rb + "," + trening.getIdTrening() + "," + iznos
                + "," + popustIznos + "," + cenaPoSatu + ",'" + datumVremePocetka + "','" + datumVremeKraja + "'";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "idRezervacija=" + rezervacija.getIdRezervacija() + " AND rb=" + rb;
    }

    @Override
    public AbstractDomainObject vratiObjekatRS(ResultSet rs) throws Exception {
        if (rs.next()) {
            Rezervacija rezervacijaObj = new Rezervacija();
            rezervacijaObj.setIdRezervacija(rs.getInt("idRezervacija"));

            Trening treningObj = new Trening();
            treningObj.setIdTrening(rs.getInt("idTrening"));

            Timestamp pocetak = rs.getTimestamp("datumVremePocetka");
            Timestamp kraj = rs.getTimestamp("datumVremeKraja");

            return new StavkaRezervacije(
                    rezervacijaObj,
                    rs.getInt("rb"),
                    treningObj,
                    rs.getBigDecimal("iznos"),
                    rs.getBigDecimal("popustIznos"),
                    rs.getBigDecimal("cenaPoSatu"),
                    pocetak != null ? pocetak.toLocalDateTime() : null,
                    kraj != null ? kraj.toLocalDateTime() : null
            );
        }
        return null;
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "idTrening=" + trening.getIdTrening() + ",iznos=" + iznos + ",popustIznos=" + popustIznos
                + ",cenaPoSatu=" + cenaPoSatu + ",datumVremePocetka='" + datumVremePocetka
                + "',datumVremeKraja='" + datumVremeKraja + "'";
    }
}
