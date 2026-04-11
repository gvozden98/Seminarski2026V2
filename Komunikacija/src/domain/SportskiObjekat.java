package domain;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SportskiObjekat implements AbstractDomainObject {

    private Integer idObjekat;
    private String naziv;
    private String adresa;
    private String email;
    private String grad;
    private String sifra;

    public SportskiObjekat() {
    }

    public SportskiObjekat(Integer idObjekat, String naziv, String adresa, String email, String grad, String sifra) {
        this.idObjekat = idObjekat;
        this.naziv = naziv;
        this.adresa = adresa;
        this.email = email;
        this.grad = grad;
        this.sifra = sifra;
    }

    public Integer getIdObjekat() {
        return idObjekat;
    }

    public void setIdObjekat(Integer idObjekat) {
        this.idObjekat = idObjekat;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    @Override
    public String vratiNazivTabele() {
        return "SportskiObjekat";
    }

    @Override
    public List<AbstractDomainObject> vratiListu(ResultSet rs) throws Exception {
        List<AbstractDomainObject> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(new SportskiObjekat(
                    rs.getInt("idObjekat"),
                    rs.getString("naziv"),
                    rs.getString("adresa"),
                    rs.getString("email"),
                    rs.getString("grad"),
                    rs.getString("sifra")
            ));
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "naziv,adresa,email,grad,sifra";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "'" + naziv + "','" + adresa + "','" + email + "','" + grad + "','" + sifra + "'";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "idObjekat=" + idObjekat;
    }

    @Override
    public AbstractDomainObject vratiObjekatRS(ResultSet rs) throws Exception {
        if (rs.next()) {
            return new SportskiObjekat(
                    rs.getInt("idObjekat"),
                    rs.getString("naziv"),
                    rs.getString("adresa"),
                    rs.getString("email"),
                    rs.getString("grad"),
                    rs.getString("sifra")
            );
        }
        return null;
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "naziv='" + naziv + "',adresa='" + adresa + "',email='" + email
                + "',grad='" + grad + "',sifra='" + sifra + "'";
    }

    @Override
    public String toString() {
        return naziv + " - " + grad;
    }
}
