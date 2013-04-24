package povoar;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPovoar extends Remote {
  
	public void recebeAgente(ICapturar capturar) throws RemoteException;

	public void reduzirQuant(Opcoes ops, int quantReduzida) throws RemoteException;

	public int pegarQuant(Opcoes ops) throws RemoteException;
	
	public String getNome() throws RemoteException; 
	
	public void addBytecode(String className, byte[] bytecode) throws RemoteException;
	
	public void loadClass(String className) throws RemoteException;

}
