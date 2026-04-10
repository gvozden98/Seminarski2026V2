package models;

import domain.StavkaRezervacije;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class StavkaRezervacijeTableModel extends AbstractTableModel {

    private final String[] columns = {
        "Redni broj",
        "Iznos stavke",
        "Vreme pocetka",
        "Vreme zavrsetka",
        "Trening",
        "Trajanje"
    };

    private List<StavkaRezervacije> stavke;

    public StavkaRezervacijeTableModel() {
        stavke = new ArrayList<StavkaRezervacije>();
    }

    public StavkaRezervacijeTableModel(List<StavkaRezervacije> stavke) {
        this.stavke = stavke;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        if (stavke == null) {
            return 0;
        }
        return stavke.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StavkaRezervacije stavka = stavke.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return stavka.getRb();
            case 1:
                return stavka.getIznos();
            case 2:
                return stavka.getDatumVremePocetka();
            case 3:
                return stavka.getDatumVremeKraja();
            case 4:
                return stavka.getTrening();
            case 5:
                return vratiTrajanje(stavka);
            default:
                return "N/A";
        }
    }

    public List<StavkaRezervacije> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaRezervacije> stavke) {
        this.stavke = stavke;
        fireTableDataChanged();
    }

    public void dodajStavku(StavkaRezervacije stavka) {
        stavke.add(stavka);
        fireTableDataChanged();
    }

    public void obrisiStavku(int red) {
        stavke.remove(red);
        renumerisiStavke();
        fireTableDataChanged();
    }

    public StavkaRezervacije vratiStavku(int red) {
        return stavke.get(red);
    }

    private void renumerisiStavke() {
        for (int i = 0; i < stavke.size(); i++) {
            stavke.get(i).setRb(i + 1);
        }
    }

    private Object vratiTrajanje(StavkaRezervacije stavka) {
        if (stavka.getDatumVremePocetka() != null && stavka.getDatumVremeKraja() != null) {
            return Duration.between(stavka.getDatumVremePocetka(), stavka.getDatumVremeKraja()).toMinutes();
        }
        if (stavka.getTrening() != null) {
            return stavka.getTrening().getTrajanjeMin();
        }
        return null;
    }
}
