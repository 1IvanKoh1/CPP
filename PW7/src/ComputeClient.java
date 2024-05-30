import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ComputeClient {
    private ComputeClient() {}

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            Compute stub = (Compute) registry.lookup("Compute");
            double response = stub.computePi(16);
            System.out.println("Відповідь: " + response);
        } catch (Exception e) {
            System.err.println("Помилка: " + e.toString());
            e.printStackTrace();
        }
    }
}
