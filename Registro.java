package povoar;

import java.rmi.Naming; 
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Registro extends UnicastRemoteObject implements IRegistro {

  protected Registro() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 6448626580568404732L;
	private List<IPovoar> lojas = new ArrayList<IPovoar>();
	
	public List<IPovoar> getLojas() throws Exception {
		return lojas;
	}

	public void addPovoar(IPovoar povoar) {
		this.lojas.add(povoar);
		try {
			System.out.println("Colonia adicionada " + povoar.getNome());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		Registro registro = new Registro();
		Naming.rebind("/localhost/"+args[0], registro);
	    System.out.println("Registrado " + args[0]);
		while(true) {
	    	Thread.sleep(1000);
	    }
	}
}
