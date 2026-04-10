package controller;

import domain.Trening;
import java.util.List;
import komunikacija.Komunikacija;

public class TreningController {

    public void ubaciTrening(Trening trening) throws Exception {
        Komunikacija.getInstance().ubaciTrening(trening);
    }

    public List<Trening> pretraziTrening(Trening kriterijum) throws Exception {
        return Komunikacija.getInstance().pretraziTrening(kriterijum);
    }

    public List<Trening> vratiListuTreninga(Trening kriterijum) throws Exception {
        return Komunikacija.getInstance().vratiListuTreninga(kriterijum);
    }

    public void promeniTrening(Trening trening) throws Exception {
        Komunikacija.getInstance().promeniTrening(trening);
    }

    public void obrisiTrening(Trening trening) throws Exception {
        Komunikacija.getInstance().obrisiTrening(trening);
    }
}
