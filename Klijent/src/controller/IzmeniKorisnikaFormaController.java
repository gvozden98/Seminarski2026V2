package controller;

import cordinator.MainCordinator;
import forme.IzmeniKorisnikaForma;

public class IzmeniKorisnikaFormaController {

    private final IzmeniKorisnikaForma izmeniKorisnikaForma;

    public IzmeniKorisnikaFormaController(IzmeniKorisnikaForma izmeniKorisnikaForma) {
        this.izmeniKorisnikaForma = izmeniKorisnikaForma;
        addActionListeners();
    }

    public void otvori() {
        izmeniKorisnikaForma.setVisible(true);
    }

    private void addActionListeners() {
        izmeniKorisnikaForma.nazadAddActionListener(e -> MainCordinator.getInstance().otvoriGlavnuFormu());
    }
}
