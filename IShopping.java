package povoar;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IShopping  extends Remote {
  public void recebeAgente(ICapturar capturar) throws RemoteException;

}
