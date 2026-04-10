package domain;

import enums.StatusRezervacije;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Rezervacija implements AbstractDomainObject {

    private Integer idRezervacija;
    private LocalDate datumKreiranja;
    private StatusRezervacije statusRezervacije;
    private BigDecimal ukupanIznos;
    private BigDecimal ukupanPopust;
    private Korisnik korisnik;
    private SportskiObjekat sportskiObjekat;

    public Rezervacija() {
    }

    public Rezervacija(Integer idRezervacija, LocalDate datumKreiranja, StatusRezervacije statusRezervacije, BigDecimal ukupanIznos, BigDecimal ukupanPopust, Korisnik korisnik, SportskiObjekat sportskiObjekat) {
        this.idRezervacija = idRezervacija;
        this.datumKreiranja = datumKreiranja;
        this.statusRezervacije = statusRezervacije;
        this.ukupanIznos = ukupanIznos;
        this.ukupanPopust = ukupanPopust;
        this.korisnik = korisnik;
        this.sportskiObjekat = sportskiObjekat;
    }

    public Integer getIdRezervacija() {
        return idRezervacija;
    }

    public void setIdRezervacija(Integer idRezervacija) {
        this.idRezervacija = idRezervacija;
    }

    public LocalDate getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(LocalDate datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    public StatusRezervacije getStatusRezervacije() {
        return statusRezervacije;
    }

    public void setStatusRezervacije(StatusRezervacije statusRezervacije) {
        this.statusRezervacije = statusRezervacije;
    }

    public BigDecimal getUkupanIznos() {
        return ukupanIznos;
    }

    public void setUkupanIznos(BigDecimal ukupanIznos) {
        this.ukupanIznos = ukupanIznos;
    }

    public BigDecimal getUkupanPopust() {
        return ukupanPopust;
    }

    public void setUkupanPopust(BigDecimal ukupanPopust) {
        this.ukupanPopust = ukupanPopust;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public SportskiObjekat getSportskiObjekat() {
        return sportskiObjekat;
    }

    public void setSportskiObjekat(SportskiObjekat sportskiObjekat) {
        this.sportskiObjekat = sportskiObjekat;
    }

    @Override
    public String vratiNazivTabele() {
        return "Rezervacija";
    }

    @Override
    public List<AbstractDomainObject> vratiListu(ResultSet rs) throws Exception {
        List<AbstractDomainObject> lista = new ArrayList<>();
        while (rs.next()) {
            Korisnik korisnikObj = new Korisnik();
            korisnikObj.setIdKorisnik(rs.getInt("idKorisnik"));

            SportskiObjekat objekat = new SportskiObjekat();
            objekat.setIdObjekat(rs.getInt("idObjekat"));

            Date datum = rs.getDate("datumKreiranja");
            String status = rs.getString("statusRezervacije");
            lista.add(new Rezervacija(
                    rs.getInt("idRezervacija"),
                    datum != null ? datum.toLocalDate() : null,
                    status != null ? StatusRezervacije.valueOf(status) : null,
                    rs.getBigDecimal("ukupanIznos"),
                    rs.getBigDecimal("ukupanPopust"),
                    korisnikObj,
                    objekat
            ));
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "idRezervacija,datumKreiranja,statusRezervacije,ukupanIznos,ukupanPopust,idKorisnik,idObjekat";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return idRezervacija + ",'" + datumKreiranja + "','" + statusRezervacije + "'," + ukupanIznos
                + "," + ukupanPopust + "," + korisnik.getIdKorisnik() + "," + sportskiObjekat.getIdObjekat();
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "idRezervacija=" + idRezervacija;
    }

    @Override
    public AbstractDomainObject vratiObjekatRS(ResultSet rs) throws Exception {
        if (rs.next()) {
            Korisnik korisnikObj = new Korisnik();
            korisnikObj.setIdKorisnik(rs.getInt("idKorisnik"));

            SportskiObjekat objekat = new SportskiObjekat();
            objekat.setIdObjekat(rs.getInt("idObjekat"));

            Date datum = rs.getDate("datumKreiranja");
            String status = rs.getString("statusRezervacije");
            return new Rezervacija(
                    rs.getInt("idRezervacija"),
                    datum != null ? datum.toLocalDate() : null,
                    status != null ? StatusRezervacije.valueOf(status) : null,
                    rs.getBigDecimal("ukupanIznos"),
                    rs.getBigDecimal("ukupanPopust"),
                    korisnikObj,
                    objekat
            );
        }
        return null;
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "datumKreiranja='" + datumKreiranja + "',statusRezervacije='" + statusRezervacije + "',ukupanIznos="
                + ukupanIznos + ",ukupanPopust=" + ukupanPopust + ",idKorisnik=" + korisnik.getIdKorisnik()
                + ",idObjekat=" + sportskiObjekat.getIdObjekat();
    }

    @Override
    public String toString() {
        return "Rezervacija #" + idRezervacija;
    }
}
