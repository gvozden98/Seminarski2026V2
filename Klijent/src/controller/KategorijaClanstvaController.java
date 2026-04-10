package controller;

import domain.KategorijaClanstva;
import java.util.List;
import komunikacija.Komunikacija;

public class KategorijaClanstvaController {

    public void ubaciKategorijaClanstva(KategorijaClanstva kategorija) throws Exception {
        Komunikacija.getInstance().ubaciKategorijaClanstva(kategorija);
    }

    public List<KategorijaClanstva> pretraziKategorijaClanstva(KategorijaClanstva kriterijum) throws Exception {
        return Komunikacija.getInstance().pretraziKategorijaClanstva(kriterijum);
    }

    public List<KategorijaClanstva> vratiListuKategorijaClanstva(KategorijaClanstva kriterijum) throws Exception {
        return Komunikacija.getInstance().vratiListuKategorijaClanstva(kriterijum);
    }

    public void promeniKategorijaClanstva(KategorijaClanstva kategorija) throws Exception {
        Komunikacija.getInstance().promeniKategorijaClanstva(kategorija);
    }

    public void obrisiKategorijaClanstva(KategorijaClanstva kategorija) throws Exception {
        Komunikacija.getInstance().obrisiKategorijaClanstva(kategorija);
    }
}
