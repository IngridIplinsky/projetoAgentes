package povoar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Random;

public class Capturar implements ICapturar{
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<IPovoar> lojas;
	private Shopping shopping;
	private int index;
	private int capacidadeRestante;
	private Produto produto;
	public List<IPovoar> getLojas() {
		return lojas;
	}
	public void setLojas(List<IPovoar> lojas) {
		this.lojas = lojas;
	}
	public Shopping getShopping() {
		return shopping;
	}
	public void setShopping(Shopping shopping) {
		this.shopping = shopping;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getCapacidadeRestante() {
		return capacidadeRestante;
	}
	public void setCapacidadeRestante(int capacidadeRestante) {
		this.capacidadeRestante = capacidadeRestante;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	
	//construtor padrao, apenas com o tipo as lojas do shopping
	public Capturar(Opcoes op, Shopping shopping, List<IPovoar> lojas) {
		this.lojas = lojas;
		this.shopping = shopping;
		Random rand = new Random();
		this.capacidadeRestante = rand.nextInt(100);
		this.produto = new Produto(op, 0, 0);
	}
	
	
	public void lancarAgente()throws RemoteException {
		if(index < lojas.size())
			try{
				lojas.get(index).recebeAgente(this);
			}catch(RemoteException e){
				lojas.get(index).addBytecode("Coletor", getBytecode("Coletor.class"));
				lojas.get(index).addBytecode("IMetropole", getBytecode("IMetropole.class"));
				lojas.get(index).loadClass("Coletor");
				lojas.get(index).recebeAgente(this);
			}
		else
			shopping.recebeAgente(this);
	}
	
	private byte[] getBytecode(String nomeArquivo) {
		File file = new File(nomeArquivo);
		byte[] data = new byte[(int) file.length()];
		 InputStream input = null;
	      try {
	        int totalBytesRead = 0;
	        FileInputStream fileInputStream = new FileInputStream(file);
	        input = new BufferedInputStream(fileInputStream);
	        while(totalBytesRead < data.length){
	          int bytesRemaining = data.length - totalBytesRead;
	          int bytesRead = input.read(data, totalBytesRead, bytesRemaining); 
	          if (bytesRead > 0){
	            totalBytesRead = totalBytesRead + bytesRead;
	          }
	        }
	      } catch (IOException e) {
			e.printStackTrace();
	      } finally {
	    	  try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	      }
		return data;
	}
	
	public synchronized void executar() throws RemoteException {
		IPovoar atual = lojas.get(index);
		int qtd = atual.pegarQuant(produto.getOps());
		if(qtd > 0) {
			int quantidadeReduzida = Math.min(qtd, capacidadeRestante);
			atual.reduzirQuant(produto.getOps(), quantidadeReduzida);
			produto = produto.aumentaEstoque(quantidadeReduzida);
			System.out.println("Capturando " + produto.getOps() + " do povoamento");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		index++;
		lancarAgente();
	}	
}
