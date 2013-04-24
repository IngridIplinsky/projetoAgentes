package povoar;

import java.rmi.Remote; 
import java.rmi.RemoteException;
import java.util.List;


public interface IRegistro extends Remote {

  public List<IPovoar> getLojas() throws Exception;

	public void addPovoar(IPovoar povoar) throws RemoteException;

}
