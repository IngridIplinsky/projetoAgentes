package povoar;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface ICapturar extends Serializable{

  public void lancarAgente() throws RemoteException;

	public void executar() throws RemoteException;

	public Produto getProduto();

}
