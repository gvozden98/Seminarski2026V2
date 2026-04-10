package komunikacija;

import java.io.Serializable;

public class PrijavaSportskiObjekatRequest implements Serializable {

    private String email;
    private String sifra;

    public PrijavaSportskiObjekatRequest() {
    }

    public PrijavaSportskiObjekatRequest(String email, String sifra) {
        this.email = email;
        this.sifra = sifra;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }
}
