package controller;

import cordinator.MainCordinator;
import forme.PretraziKorisnikeForma;

public class PretraziKorisnikeFormaController {

    private final PretraziKorisnikeForma pretraziKorisnikeForma;

    public PretraziKorisnikeFormaController(PretraziKorisnikeForma pretraziKorisnikeForma) {
        this.pretraziKorisnikeForma = pretraziKorisnikeForma;
        addActionListeners();
    }

    public void otvori() {
        pretraziKorisnikeForma.setVisible(true);
    }

    private void addActionListeners() {
        pretraziKorisnikeForma.nazadAddActionListener(e -> MainCordinator.getInstance().otvoriGlavnuFormu());
    }
}
