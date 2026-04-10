package konfiguracija;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Konfiguracija {

    private static Konfiguracija instance;
    private final Properties properties;
    private final String putanja;

    private Konfiguracija() {
        properties = new Properties();
        putanja = "src/konfiguracija/properties.properties";
        ucitaj();
    }

    public static Konfiguracija getInstance() {
        if (instance == null) {
            instance = new Konfiguracija();
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key, "");
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public void sacuvajIzmene() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(putanja)) {
            properties.store(fos, null);
        }
    }

    private void ucitaj() {
        try (FileInputStream fis = new FileInputStream(putanja)) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Greska pri ucitavanju konfiguracije: " + e.getMessage());
        }
    }
}
