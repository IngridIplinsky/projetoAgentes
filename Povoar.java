package povoar;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import classes.Host;

@SuppressWarnings("serial")
public class Povoar  extends UnicastRemoteObject implements IPovoar {
  
	private Map<Opcoes, Produto> produtos;
	private Random rand;
	private String nome;
	
	public Map<Opcoes, Produto> getProdutos() {
		return produtos;
	}
	public void setProdutos(Map<Opcoes, Produto> produtos) {
		this.produtos = produtos;
	}
	public Random getRand() {
		return rand;
	}
	public void setRand(Random rand) {
		this.rand = rand;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Povoar(String nome)throws RemoteException {
		produtos = new HashMap<Opcoes, Produto>();
		rand = new Random();
		this.nome = nome;
	}
	
	//qnd nao tem uma opcao desejada adiciona esta
	public void addOpcao(Opcoes op) throws Exception {
		if(!produtos.containsKey(op)) {
			Produto produtoNovo = new Produto(op, 0, 0);
			produtos.put(op, produtoNovo);
			IRegistro registro = (IRegistro)Naming.lookup("rmi://"+Host.HOST+"/"+op.toString());
			registro.addPovoar(this);
		}
	}
	//qnd se produz uma certa qtd de um determinado produto, 
	//tem que aumentar o estoque
	public void produzir(int qtd) {
		for(Opcoes op : produtos.keySet()) {
			Produto atual = produtos.get(op);
			Produto novo = atual.aumentaEstoque(qtd);
			produtos.put(op, novo);
		}
	}
	
	// para receber o agente eh soh executar
	public void recebeAgente(ICapturar cap)throws RemoteException {
		cap.executar();
	}
	
	// qntidade de um produto
	public int pegarQuant(Opcoes op) {
		if(!produtos.containsKey(op))
			return 0;
		else {
			return produtos.get(op).getQuant();
		}
	}
	
	// diminuinto um produto
	public void reduzirQuant(Opcoes op, int quantReduzida) {
		Produto atual = produtos.get(op);
		Produto quantNova = atual.diminuiEstoque(quantReduzida);
		produtos.put(op, quantNova);
	}
	
	//imprimindo melhor
	public String toString() {
		StringBuilder pov = new StringBuilder("Povoamento ");
		pov.append(nome);
		pov.append("\n");
		for(Produto p : produtos.values()) {
			pov.append(p);
			pov.append("\n");
		}
		return pov.toString();
	}
	
	public void addBytecode(String className, byte[] bytecode) {
		try {
			OutputStream output = null;
			try {
				FileOutputStream fos = new FileOutputStream(className + ".class");
				output = new BufferedOutputStream(fos);
				output.write(bytecode);
			}
			finally {
				output.close();
			}
		}
		catch(FileNotFoundException ex){
			ex.printStackTrace();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	public void loadClass(String className) throws RemoteException {
		MemoryClassLoader loader = new MemoryClassLoader(ClassLoader.getSystemClassLoader());
		loader.loadRemoteClass(className);
	}
	
	public static void main(String[] args) throws Exception {
		Povoar riachuelo = new Povoar("Riachuelo");
		Povoar pinkbiju = new Povoar("Pinkbiju");
		Povoar botcario = new Povoar("Botcario");
		Povoar savan = new Povoar("Savan");
		Povoar brooksfield = new Povoar("Brooksfield");
		Povoar mariza = new Povoar("Mariza");
		
		riachuelo.addOpcao(Opcoes.acessorio);
		riachuelo.addOpcao(Opcoes.perfumaria);
		riachuelo.addOpcao(Opcoes.roupa_feminina);
		riachuelo.addOpcao(Opcoes.roupa_masculina);
		riachuelo.addOpcao(Opcoes.sapato);
		
		pinkbiju.addOpcao(Opcoes.acessorio);		
		botcario.addOpcao(Opcoes.perfumaria);
		savan.addOpcao(Opcoes.sapato);
		
		brooksfield.addOpcao(Opcoes.roupa_masculina);
		brooksfield.addOpcao(Opcoes.acessorio);
		
		mariza.addOpcao(Opcoes.roupa_feminina);
		mariza.addOpcao(Opcoes.acessorio);
		mariza.addOpcao(Opcoes.perfumaria);
		
		riachuelo.produzir(1000);
		pinkbiju.produzir(100);
		botcario.produzir(400);
		savan.produzir(200);
		brooksfield.produzir(500);
		mariza.produzir(600);
		
		System.out.println(rachuelo);
		System.out.println(pinkbiju);
		System.out.println(botcario);
		System.out.println(savan);
		System.out.println(brooksfield);
		System.out.println(mariza);
	}
}
