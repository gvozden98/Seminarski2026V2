package controller;

import komunikacija.Komunikacija;
import komunikacija.UbaciTipOpremeRequest;

public class TipOpremeController {

    public void ubaciTipOpreme(UbaciTipOpremeRequest request) throws Exception {
        Komunikacija.getInstance().ubaciTipOpreme(request);
    }
}
