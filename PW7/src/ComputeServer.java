import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ComputeServer {
    public static void main(String[] args) {
        try {
            ComputeImpl obj = new ComputeImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Compute", obj);
            System.out.println("Обчислювальний сервер готовий");
        } catch (Exception e) {
            System.err.println("Помилка: " + e.toString());
            e.printStackTrace();
        }
    }
}
