package thread;

import controller.ServerController;
import domain.KategorijaClanstva;
import domain.Korisnik;
import domain.Rezervacija;
import domain.SportskiObjekat;
import domain.TipOpreme;
import domain.Trening;
import java.io.EOFException;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import komunikacija.KreirajRezervacijuRequest;
import komunikacija.PrijavaSportskiObjekatRequest;
import komunikacija.Receiver;
import komunikacija.Request;
import komunikacija.Response;
import komunikacija.Sender;
import server.Server;

public class ClientThread extends Thread {

    private final Socket socket;
    private final Sender sender;
    private final Receiver receiver;
    private final Server server;
    private boolean running;

    public ClientThread(Socket socket, Server server) throws Exception {
        this.socket = socket;
        this.server = server;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                Object raw = receiver.receive();
                if (!(raw instanceof Request request)) {
                    running = false;
                    break;
                }

                Response response = new Response();
                try {
                    obradiRequest(request, response);
                } catch (Exception e) {
                    response.setException(e);
                }
                sender.send(response);
            } catch (EOFException | SocketException e) {
                running = false;
            } catch (Exception e) {
                System.out.println("Greska u klijentskoj niti: " + e.getMessage());
                running = false;
            }
        }
        stopRunning();
    }

    private void obradiRequest(Request request, Response response) throws Exception {
        switch (request.getOperation()) {
            case PRIJAVI_SPORTSKI_OBJEKAT -> {
                PrijavaSportskiObjekatRequest zahtev = (PrijavaSportskiObjekatRequest) request.getArgument();
                SportskiObjekat objekat = ServerController.getInstance().prijaviSportskiObjekat(zahtev);
                response.setResponse(objekat);
            }
            case UBACI_REZERVACIJU -> {
                KreirajRezervacijuRequest zahtev = (KreirajRezervacijuRequest) request.getArgument();
                response.setResponse(ServerController.getInstance().ubaciRezervaciju(zahtev));
            }
            case PRETRAZI_REZERVACIJU -> {
                Rezervacija kriterijum = (Rezervacija) request.getArgument();
                List<Rezervacija> rezervacije = ServerController.getInstance().pretraziRezervaciju(kriterijum);
                response.setResponse(rezervacije);
            }
            case VRATI_LISTU_REZERVACIJA -> {
                List<Rezervacija> rezervacije = ServerController.getInstance().vratiListuRezervacija(request.getArgument());
                response.setResponse(rezervacije);
            }
            case PROMENI_REZERVACIJU -> {
                ServerController.getInstance().promeniRezervaciju((Rezervacija) request.getArgument());
                response.setResponse(null);
            }
            case VRATI_LISTU_SVI_SPORTSKI_OBJEKAT -> response.setResponse(ServerController.getInstance().vratiListuSvihSportskihObjekata());
            case VRATI_LISTU_SVI_KORISNIK -> response.setResponse(ServerController.getInstance().vratiListuSvihKorisnika());
            case VRATI_LISTU_SVI_TRENING -> response.setResponse(ServerController.getInstance().vratiListuSvihTreninga());
            case VRATI_LISTU_SVI_KATEGORIJA_CLANSTVA -> response.setResponse(ServerController.getInstance().vratiListuSvihKategorijaClanstva());
            case UBACI_KORISNIK -> {
                ServerController.getInstance().ubaciKorisnik((Korisnik) request.getArgument());
                response.setResponse(null);
            }
            case PROMENI_KORISNIK -> {
                ServerController.getInstance().promeniKorisnik((Korisnik) request.getArgument());
                response.setResponse(null);
            }
            case PRETRAZI_KORISNIK -> {
                List<Korisnik> korisnici = ServerController.getInstance().pretraziKorisnik((Korisnik) request.getArgument());
                response.setResponse(korisnici);
            }
            case VRATI_LISTU_KORISNIK -> {
                List<Korisnik> korisnici = ServerController.getInstance().vratiListuKorisnika((Korisnik) request.getArgument());
                response.setResponse(korisnici);
            }
            case OBRISI_KORISNIK -> {
                ServerController.getInstance().obrisiKorisnik((Korisnik) request.getArgument());
                response.setResponse(null);
            }
            case PRETRAZI_SPORTSKI_OBJEKAT -> {
                List<SportskiObjekat> objekti = ServerController.getInstance().pretraziSportskiObjekat((SportskiObjekat) request.getArgument());
                response.setResponse(objekti);
            }
            case VRATI_LISTU_SPORTSKI_OBJEKAT -> {
                List<SportskiObjekat> objekti = ServerController.getInstance().vratiListuSportskihObjekata(request.getArgument());
                response.setResponse(objekti);
            }
            case UBACI_TRENING -> {
                ServerController.getInstance().ubaciTrening((Trening) request.getArgument());
                response.setResponse(null);
            }
            case PRETRAZI_TRENING -> {
                List<Trening> treninzi = ServerController.getInstance().pretraziTrening((Trening) request.getArgument());
                response.setResponse(treninzi);
            }
            case VRATI_LISTU_TRENING -> {
                List<Trening> treninzi = ServerController.getInstance().vratiListuTreninga((Trening) request.getArgument());
                response.setResponse(treninzi);
            }
            case PROMENI_TRENING -> {
                ServerController.getInstance().promeniTrening((Trening) request.getArgument());
                response.setResponse(null);
            }
            case OBRISI_TRENING -> {
                ServerController.getInstance().obrisiTrening((Trening) request.getArgument());
                response.setResponse(null);
            }
            case UBACI_KATEGORIJA_CLANSTVA -> {
                ServerController.getInstance().ubaciKategorijaClanstva((KategorijaClanstva) request.getArgument());
                response.setResponse(null);
            }
            case PRETRAZI_KATEGORIJA_CLANSTVA -> {
                List<KategorijaClanstva> kategorije = ServerController.getInstance().pretraziKategorijaClanstva((KategorijaClanstva) request.getArgument());
                response.setResponse(kategorije);
            }
            case VRATI_LISTU_KATEGORIJA_CLANSTVA -> {
                List<KategorijaClanstva> kategorije = ServerController.getInstance().vratiListuKategorijaClanstva((KategorijaClanstva) request.getArgument());
                response.setResponse(kategorije);
            }
            case PROMENI_KATEGORIJA_CLANSTVA -> {
                ServerController.getInstance().promeniKategorijaClanstva((KategorijaClanstva) request.getArgument());
                response.setResponse(null);
            }
            case OBRISI_KATEGORIJA_CLANSTVA -> {
                ServerController.getInstance().obrisiKategorijaClanstva((KategorijaClanstva) request.getArgument());
                response.setResponse(null);
            }
            case UBACI_TIP_OPREME -> {
                ServerController.getInstance().ubaciTipOpreme((TipOpreme) request.getArgument());
                response.setResponse(null);
            }
            case PRETRAZI_TIP_OPREME -> {
                List<TipOpreme> tipovi = ServerController.getInstance().pretraziTipOpreme((TipOpreme) request.getArgument());
                response.setResponse(tipovi);
            }
            case VRATI_LISTU_TIP_OPREME -> {
                List<TipOpreme> tipovi = ServerController.getInstance().vratiListuTipOpreme((TipOpreme) request.getArgument());
                response.setResponse(tipovi);
            }
            case PROMENI_TIP_OPREME -> {
                ServerController.getInstance().promeniTipOpreme((TipOpreme) request.getArgument());
                response.setResponse(null);
            }
            default -> throw new Exception("Nepoznata operacija.");
        }
    }

    public void stopRunning() {
        running = false;
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("Greska pri zatvaranju soketa: " + e.getMessage());
        } finally {
            server.removeClient(this);
        }
    }
}
