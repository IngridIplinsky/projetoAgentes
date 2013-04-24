package povoar;

import java.rmi.Naming;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Shopping {
  private String nome;
	private Map<Opcoes, Produto> produtos;
	private int poder;
	private Random rand;
	private int rodada;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Map<Opcoes, Produto> getProdutos() {
		return produtos;
	}
	public void setProdutos(Map<Opcoes, Produto> produtos) {
		this.produtos = produtos;
	}
	public int getPoder() {
		return poder;
	}
	public void setPoder(int poder) {
		this.poder = poder;
	}
	public Random getRand() {
		return rand;
	}
	public void setRand(Random rand) {
		this.rand = rand;
	}
	public int getRodada() {
		return rodada;
	}
	public void setRodada(int rodada) {
		this.rodada = rodada;
	}

	public Shopping(String nome) {
		this.nome = nome;
		produtos = new HashMap<Opcoes, Produto>();
		produtos.put(Opcoes.acessorio, new Produto(Opcoes.acessorio, 0, 0));
		produtos.put(Opcoes.perfumaria, new Produto(Opcoes.perfumaria, 0, 0));
		produtos.put(Opcoes.roupa_masculina, new Produto(Opcoes.roupa_masculina, 0, 0));
		produtos.put(Opcoes.roupa_feminina, new Produto(Opcoes.roupa_feminina, 0, 0));
		produtos.put(Opcoes.sapato, new Produto(Opcoes.sapato, 0, 0));
		poder = 0;
		rand = new Random();
	}
	
	public void lancarAgente(Opcoes op)throws Exception  {
		String nome = op.toString();
		IRegistro registro = (IRegistro)Naming.lookup("rmi://"+Host.HOST+"/"+nome);
		List<IPovoar> lojas = registro.getLojas();
		Capturar novo = new Capturar(op, this, lojas);
		novo.lancarAgente();
	}
	
	public void diminuiEstoque()throws Exception {
		int disponivel = 1;
		int necessario = rand.nextInt();
		for(Produto p : produtos.values()) {
			if(p.disponibilidade(necessario) == -1) {
				disponivel = -1;
				if((rodada % 20) == 0)
					lancarAgente(p.getOps());
			}
		}
		if(disponivel==1) {
			poder++;
			for(Opcoes op : produtos.keySet()) {
				Produto quantNova = produtos.get(op).diminuiEstoque(necessario);
				produtos.put(op, quantNova);
			}
		}
		rodada++;
	}
	
	public void recebeAgente(ICapturar cap) {
		Produto novo = cap.getProduto();
		Produto atual = produtos.get(novo.getOps());
		atual = atual.aumentaEstoque(novo.getQuant());
		produtos.put(atual.getOps(), atual);
	}
	
	public static void main(String[] args) throws Exception {
		Shopping centerShopping = new Shopping("CenterShopping");
		Shopping uberlandiaShopping = new Shopping("UberlandiaShopping");
		centerShopping.diminuiEstoque();
		uberlandiaShopping.diminuiEstoque();
		System.out.println(centerShopping);
		System.out.println(uberlandiaShopping);
	}
}
