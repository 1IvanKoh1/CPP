import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class ComputeImpl extends UnicastRemoteObject implements Compute {

    protected ComputeImpl() throws RemoteException {
        super();
    }

    @Override
    public double computePi(int digits) throws RemoteException {
        return Math.PI;
    }
}
