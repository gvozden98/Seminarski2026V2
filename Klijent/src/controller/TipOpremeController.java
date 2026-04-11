package controller;

import domain.TipOpreme;
import java.util.List;
import komunikacija.Komunikacija;
import komunikacija.UbaciTipOpremeRequest;

public class TipOpremeController {

    public void ubaciTipOpreme(UbaciTipOpremeRequest request) throws Exception {
        Komunikacija.getInstance().ubaciTipOpreme(request);
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
