package controller;

import domain.TipOpreme;
import java.util.List;
import komunikacija.Komunikacija;

public class TipOpremeController {

    public void ubaciTipOpreme(TipOpreme tipOpreme) throws Exception {
        Komunikacija.getInstance().ubaciTipOpreme(tipOpreme);
    }

    public List<TipOpreme> pretraziTipOpreme(TipOpreme kriterijum) throws Exception {
        return Komunikacija.getInstance().pretraziTipOpreme(kriterijum);
    }

    public List<TipOpreme> vratiListuTipOpreme(TipOpreme kriterijum) throws Exception {
        return Komunikacija.getInstance().vratiListuTipOpreme(kriterijum);
    }

    public void promeniTipOpreme(TipOpreme tipOpreme) throws Exception {
        Komunikacija.getInstance().promeniTipOpreme(tipOpreme);
    }
}
