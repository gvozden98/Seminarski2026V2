package komunikacija;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class Sender {

    private final Socket socket;
    private ObjectOutputStream out;

    public Sender(Socket socket) throws Exception {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
    }

    public void send(Object object) throws Exception {
        out.writeObject(object);
        out.flush();
    }
}
