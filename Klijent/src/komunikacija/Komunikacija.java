package komunikacija;

import domain.KategorijaClanstva;
import domain.Korisnik;
import domain.Rezervacija;
import domain.SportskiObjekat;
import domain.TipOpreme;
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
    public List<Rezervacija> pretraziRezervaciju(Rezervacija kriterijum) throws Exception {
        return (List<Rezervacija>) posaljiRequest(new Request(Operation.PRETRAZI_REZERVACIJU, kriterijum));
    }

    @SuppressWarnings("unchecked")
    public List<Rezervacija> vratiListuRezervacija(Object kriterijum) throws Exception {
        return (List<Rezervacija>) posaljiRequest(new Request(Operation.VRATI_LISTU_REZERVACIJA, kriterijum));
    }

    public void promeniRezervaciju(Rezervacija rezervacija) throws Exception {
        posaljiRequest(new Request(Operation.PROMENI_REZERVACIJU, rezervacija));
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

    @SuppressWarnings("unchecked")
    public List<SportskiObjekat> pretraziSportskiObjekat(SportskiObjekat kriterijum) throws Exception {
        return (List<SportskiObjekat>) posaljiRequest(new Request(Operation.PRETRAZI_SPORTSKI_OBJEKAT, kriterijum));
    }

    @SuppressWarnings("unchecked")
    public List<SportskiObjekat> vratiListuSportskihObjekata(Object kriterijum) throws Exception {
        return (List<SportskiObjekat>) posaljiRequest(new Request(Operation.VRATI_LISTU_SPORTSKI_OBJEKAT, kriterijum));
    }

    public void ubaciTrening(Trening trening) throws Exception {
        posaljiRequest(new Request(Operation.UBACI_TRENING, trening));
    }

    @SuppressWarnings("unchecked")
    public List<Trening> pretraziTrening(Trening kriterijum) throws Exception {
        return (List<Trening>) posaljiRequest(new Request(Operation.PRETRAZI_TRENING, kriterijum));
    }

    @SuppressWarnings("unchecked")
    public List<Trening> vratiListuTreninga(Trening kriterijum) throws Exception {
        return (List<Trening>) posaljiRequest(new Request(Operation.VRATI_LISTU_TRENING, kriterijum));
    }

    public void promeniTrening(Trening trening) throws Exception {
        posaljiRequest(new Request(Operation.PROMENI_TRENING, trening));
    }

    public void obrisiTrening(Trening trening) throws Exception {
        posaljiRequest(new Request(Operation.OBRISI_TRENING, trening));
    }

    public void ubaciKategorijaClanstva(KategorijaClanstva kategorija) throws Exception {
        posaljiRequest(new Request(Operation.UBACI_KATEGORIJA_CLANSTVA, kategorija));
    }

    @SuppressWarnings("unchecked")
    public List<KategorijaClanstva> pretraziKategorijaClanstva(KategorijaClanstva kriterijum) throws Exception {
        return (List<KategorijaClanstva>) posaljiRequest(new Request(Operation.PRETRAZI_KATEGORIJA_CLANSTVA, kriterijum));
    }

    @SuppressWarnings("unchecked")
    public List<KategorijaClanstva> vratiListuKategorijaClanstva(KategorijaClanstva kriterijum) throws Exception {
        return (List<KategorijaClanstva>) posaljiRequest(new Request(Operation.VRATI_LISTU_KATEGORIJA_CLANSTVA, kriterijum));
    }

    public void promeniKategorijaClanstva(KategorijaClanstva kategorija) throws Exception {
        posaljiRequest(new Request(Operation.PROMENI_KATEGORIJA_CLANSTVA, kategorija));
    }

    public void obrisiKategorijaClanstva(KategorijaClanstva kategorija) throws Exception {
        posaljiRequest(new Request(Operation.OBRISI_KATEGORIJA_CLANSTVA, kategorija));
    }

    public void ubaciTipOpreme(TipOpreme tipOpreme) throws Exception {
        posaljiRequest(new Request(Operation.UBACI_TIP_OPREME, tipOpreme));
    }

    @SuppressWarnings("unchecked")
    public List<TipOpreme> pretraziTipOpreme(TipOpreme kriterijum) throws Exception {
        return (List<TipOpreme>) posaljiRequest(new Request(Operation.PRETRAZI_TIP_OPREME, kriterijum));
    }

    @SuppressWarnings("unchecked")
    public List<TipOpreme> vratiListuTipOpreme(TipOpreme kriterijum) throws Exception {
        return (List<TipOpreme>) posaljiRequest(new Request(Operation.VRATI_LISTU_TIP_OPREME, kriterijum));
    }

    public void promeniTipOpreme(TipOpreme tipOpreme) throws Exception {
        posaljiRequest(new Request(Operation.PROMENI_TIP_OPREME, tipOpreme));
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
