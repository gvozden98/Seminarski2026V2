package komunikacija;

import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver {

    private final Socket socket;
    private ObjectInputStream in;

    public Receiver(Socket socket) throws Exception {
        this.socket = socket;
        in = new ObjectInputStream(socket.getInputStream());
    }

    public Object receive() throws Exception {
        return in.readObject();
    }
}
