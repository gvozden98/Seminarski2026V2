package thread;

import controller.ServerController;
import domain.Korisnik;
import domain.Rezervacija;
import domain.StavkaRezervacije;
import domain.SportskiObjekat;
import java.io.EOFException;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import komunikacija.KreirajRezervacijuRequest;
import komunikacija.PromeniRezervacijuRequest;
import komunikacija.PretraziRezervacijuRequest;
import komunikacija.PrijavaSportskiObjekatRequest;
import komunikacija.UbaciTipOpremeRequest;
import komunikacija.Receiver;
import komunikacija.RezervacijaPretraga;
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
                PretraziRezervacijuRequest kriterijum = (PretraziRezervacijuRequest) request.getArgument();
                List<RezervacijaPretraga> rezervacije = ServerController.getInstance().pretraziRezervaciju(kriterijum);
                response.setResponse(rezervacije);
            }
            case VRATI_LISTU_REZERVACIJA -> {
                List<Rezervacija> rezervacije = ServerController.getInstance().vratiListuRezervacija(request.getArgument());
                response.setResponse(rezervacije);
            }
            case VRATI_LISTU_STAVKI_REZERVACIJE -> {
                List<StavkaRezervacije> stavke = ServerController.getInstance().vratiListuStavkiRezervacije((Rezervacija) request.getArgument());
                response.setResponse(stavke);
            }
            case PROMENI_REZERVACIJU -> {
                ServerController.getInstance().promeniRezervaciju((PromeniRezervacijuRequest) request.getArgument());
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
            case UBACI_TIP_OPREME -> {
                ServerController.getInstance().ubaciTipOpreme((UbaciTipOpremeRequest) request.getArgument());
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
