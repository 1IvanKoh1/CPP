import java.net.*;

public class UDPEchoClient {

    public static final int PORT = 7;

    public static void main(String[] args) {
        String hostname = "localhost";
        if (args.length > 0) {
            hostname = args[0];
        }
        try {
            InetAddress ia = InetAddress.getByName(hostname);
            DatagramSocket socket = new DatagramSocket();
            Thread sender = new SenderThread(socket, ia, PORT);
            sender.start();
            Thread receiver = new ReceiverThread(socket);
            receiver.start();
        } catch (UnknownHostException | SocketException ex) {
            System.err.println(ex);
        }
    }
}
