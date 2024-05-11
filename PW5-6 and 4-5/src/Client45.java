import java.io.*;
import java.net.*;

public class Client45 {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            int number = 20;
            out.writeInt(number);
            out.flush();
            System.out.println("Sent task to server: " + number);

            long result = in.readLong();
            System.out.println("Received result from server: " + result);

            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
