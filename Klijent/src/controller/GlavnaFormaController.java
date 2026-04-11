package controller;

import cordinator.MainCordinator;
import forme.GlavnaForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GlavnaFormaController {

    private final GlavnaForma glavnaForma;

    public GlavnaFormaController(GlavnaForma glavnaForma) {
        this.glavnaForma = glavnaForma;
        addActionListeners();
    }

    public void otvori() {
        glavnaForma.setVisible(true);
    }

    public GlavnaForma getGlavnaForma() {
        return glavnaForma;
    }

    private void addActionListeners() {
        glavnaForma.rezervacijeAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCordinator.getInstance().zapocniKreiranjeRezervacije();
            }
        });
    }
}
