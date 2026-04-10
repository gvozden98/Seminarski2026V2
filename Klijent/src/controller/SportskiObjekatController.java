package controller;

import domain.SportskiObjekat;
import java.util.List;
import komunikacija.Komunikacija;

public class SportskiObjekatController {

    public List<SportskiObjekat> pretraziSportskiObjekat(SportskiObjekat kriterijum) throws Exception {
        return Komunikacija.getInstance().pretraziSportskiObjekat(kriterijum);
    }

    public List<SportskiObjekat> vratiListuSportskihObjekata(Object kriterijum) throws Exception {
        return Komunikacija.getInstance().vratiListuSportskihObjekata(kriterijum);
    }

    public List<SportskiObjekat> vratiListuSvihSportskihObjekata() throws Exception {
        return Komunikacija.getInstance().vratiListuSvihSportskihObjekata();
    }
}
