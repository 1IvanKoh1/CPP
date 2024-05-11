import java.net.*;
import java.util.*;

public class UDPServer {
    private static List<String> registeredClients = new ArrayList<>();

    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(9876);
            byte[] receiveData = new byte[1024];
            byte[] sendData;
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                String clientRequest = new String(receivePacket.getData(), 0, receivePacket.getLength());
                if (clientRequest.equals("REGISTER")) {
                    if (!registeredClients.contains(clientAddress.getHostAddress() + ":" + clientPort)) {
                        registeredClients.add(clientAddress.getHostAddress() + ":" + clientPort);
                    }
                    String response = "Registered clients: " + registeredClients.toString();
                    sendData = response.getBytes();
                } else {
                    sendData = "Invalid request".getBytes();
                }
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                socket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
