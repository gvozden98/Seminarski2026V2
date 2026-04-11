package domain;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Korisnik implements AbstractDomainObject {

    private Integer idKorisnik;
    private String ime;
    private String prezime;
    private String email;
    private String telefon;
    private LocalDate datumRegistracije;
    private KategorijaClanstva kategorijaClanstva;

    public Korisnik() {
    }

    public Korisnik(Integer idKorisnik, String ime, String prezime, String email, String telefon, LocalDate datumRegistracije, KategorijaClanstva kategorijaClanstva) {
        this.idKorisnik = idKorisnik;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.telefon = telefon;
        this.datumRegistracije = datumRegistracije;
        this.kategorijaClanstva = kategorijaClanstva;
    }

    public Integer getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(Integer idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public LocalDate getDatumRegistracije() {
        return datumRegistracije;
    }

    public void setDatumRegistracije(LocalDate datumRegistracije) {
        this.datumRegistracije = datumRegistracije;
    }

    public KategorijaClanstva getKategorijaClanstva() {
        return kategorijaClanstva;
    }

    public void setKategorijaClanstva(KategorijaClanstva kategorijaClanstva) {
        this.kategorijaClanstva = kategorijaClanstva;
    }

    @Override
    public String vratiNazivTabele() {
        return "Korisnik";
    }

    @Override
    public List<AbstractDomainObject> vratiListu(ResultSet rs) throws Exception {
        List<AbstractDomainObject> lista = new ArrayList<>();
        while (rs.next()) {
            KategorijaClanstva kc = new KategorijaClanstva();
            kc.setIdKC(rs.getInt("idKC"));

            Date datum = rs.getDate("datumRegistracije");
            lista.add(new Korisnik(
                    rs.getInt("idKorisnik"),
                    rs.getString("ime"),
                    rs.getString("prezime"),
                    rs.getString("email"),
                    rs.getString("telefon"),
                    datum != null ? datum.toLocalDate() : null,
                    kc
            ));
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "ime,prezime,email,telefon,datumRegistracije,idKC";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "'" + ime + "','" + prezime + "','" + email + "','" + telefon
                + "','" + datumRegistracije + "'," + kategorijaClanstva.getIdKC();
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "idKorisnik=" + idKorisnik;
    }

    @Override
    public AbstractDomainObject vratiObjekatRS(ResultSet rs) throws Exception {
        if (rs.next()) {
            KategorijaClanstva kc = new KategorijaClanstva();
            kc.setIdKC(rs.getInt("idKC"));
            Date datum = rs.getDate("datumRegistracije");
            return new Korisnik(
                    rs.getInt("idKorisnik"),
                    rs.getString("ime"),
                    rs.getString("prezime"),
                    rs.getString("email"),
                    rs.getString("telefon"),
                    datum != null ? datum.toLocalDate() : null,
                    kc
            );
        }
        return null;
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "ime='" + ime + "',prezime='" + prezime + "',email='" + email + "',telefon='" + telefon
                + "',datumRegistracije='" + datumRegistracije + "',idKC=" + kategorijaClanstva.getIdKC();
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }
}
