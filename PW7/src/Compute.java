import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Compute extends Remote {
    double computePi(int digits) throws RemoteException;
}
