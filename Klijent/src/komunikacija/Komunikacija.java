package komunikacija;

import domain.KategorijaClanstva;
import domain.Korisnik;
import domain.Rezervacija;
import domain.SportskiObjekat;
import domain.StavkaRezervacije;
import domain.Trening;
import enums.Operation;
import java.net.Socket;
import java.util.List;

public class Komunikacija {

    private static Komunikacija instance;

    private Socket socket;
    private Sender sender;
    private Receiver receiver;

    private Komunikacija() {
    }

    public static Komunikacija getInstance() {
        if (instance == null) {
            instance = new Komunikacija();
        }
        return instance;
    }

    public void connect() throws Exception {
        if (socket != null && socket.isConnected() && !socket.isClosed()) {
            return;
        }
        socket = new Socket("127.0.0.1", 9000);
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }

    public void disconnect() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("Greska pri prekidu konekcije: " + e.getMessage());
        } finally {
            socket = null;
            sender = null;
            receiver = null;
        }
    }

    public SportskiObjekat prijaviSportskiObjekat(String email, String sifra) throws Exception {
        return (SportskiObjekat) posaljiRequest(new Request(Operation.PRIJAVI_SPORTSKI_OBJEKAT, new PrijavaSportskiObjekatRequest(email, sifra)));
    }

    public Rezervacija ubaciRezervaciju(KreirajRezervacijuRequest requestData) throws Exception {
        return (Rezervacija) posaljiRequest(new Request(Operation.UBACI_REZERVACIJU, requestData));
    }

    @SuppressWarnings("unchecked")
    public List<RezervacijaPretraga> pretraziRezervaciju(PretraziRezervacijuRequest kriterijum) throws Exception {
        return (List<RezervacijaPretraga>) posaljiRequest(new Request(Operation.PRETRAZI_REZERVACIJU, kriterijum));
    }

    @SuppressWarnings("unchecked")
    public List<Rezervacija> vratiListuRezervacija(Object kriterijum) throws Exception {
        return (List<Rezervacija>) posaljiRequest(new Request(Operation.VRATI_LISTU_REZERVACIJA, kriterijum));
    }

    @SuppressWarnings("unchecked")
    public List<StavkaRezervacije> vratiListuStavkiRezervacije(Rezervacija rezervacija) throws Exception {
        return (List<StavkaRezervacije>) posaljiRequest(new Request(Operation.VRATI_LISTU_STAVKI_REZERVACIJE, rezervacija));
    }

    public void promeniRezervaciju(PromeniRezervacijuRequest request) throws Exception {
        posaljiRequest(new Request(Operation.PROMENI_REZERVACIJU, request));
    }

    @SuppressWarnings("unchecked")
    public List<SportskiObjekat> vratiListuSvihSportskihObjekata() throws Exception {
        return (List<SportskiObjekat>) posaljiRequest(new Request(Operation.VRATI_LISTU_SVI_SPORTSKI_OBJEKAT, null));
    }

    @SuppressWarnings("unchecked")
    public List<Korisnik> vratiListuSvihKorisnika() throws Exception {
        return (List<Korisnik>) posaljiRequest(new Request(Operation.VRATI_LISTU_SVI_KORISNIK, null));
    }

    @SuppressWarnings("unchecked")
    public List<Trening> vratiListuSvihTreninga() throws Exception {
        return (List<Trening>) posaljiRequest(new Request(Operation.VRATI_LISTU_SVI_TRENING, null));
    }

    @SuppressWarnings("unchecked")
    public List<KategorijaClanstva> vratiListuSvihKategorijaClanstva() throws Exception {
        return (List<KategorijaClanstva>) posaljiRequest(new Request(Operation.VRATI_LISTU_SVI_KATEGORIJA_CLANSTVA, null));
    }

    public void ubaciKorisnik(Korisnik korisnik) throws Exception {
        posaljiRequest(new Request(Operation.UBACI_KORISNIK, korisnik));
    }

    public void promeniKorisnik(Korisnik korisnik) throws Exception {
        posaljiRequest(new Request(Operation.PROMENI_KORISNIK, korisnik));
    }

    @SuppressWarnings("unchecked")
    public List<Korisnik> pretraziKorisnik(Korisnik kriterijum) throws Exception {
        return (List<Korisnik>) posaljiRequest(new Request(Operation.PRETRAZI_KORISNIK, kriterijum));
    }

    @SuppressWarnings("unchecked")
    public List<Korisnik> vratiListuKorisnika(Korisnik kriterijum) throws Exception {
        return (List<Korisnik>) posaljiRequest(new Request(Operation.VRATI_LISTU_KORISNIK, kriterijum));
    }

    public void obrisiKorisnik(Korisnik korisnik) throws Exception {
        posaljiRequest(new Request(Operation.OBRISI_KORISNIK, korisnik));
    }

    public void ubaciTipOpreme(UbaciTipOpremeRequest request) throws Exception {
        posaljiRequest(new Request(Operation.UBACI_TIP_OPREME, request));
    }

    private Object posaljiRequest(Request request) throws Exception {
        if (sender == null || receiver == null) {
            throw new Exception("Klijent nije povezan sa serverom.");
        }
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
        return response.getResponse();
    }
}
