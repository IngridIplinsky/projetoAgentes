package povoar;

import java.io.Serializable;


public class Produto implements Serializable{
  private static final long serialVersionUID = 1L;
	private Opcoes ops;
	private int quant;
	private double precoMedio;
	
	// get e set dos atributos
	public Opcoes getOps() {
		return ops;
	}
	public void setOps(Opcoes ops) {
		this.ops = ops;
	}
	public int getQuant() {
		return quant;
	}
	public void setQuant(int quant) {
		this.quant = quant;
	}
	public void setPrecoMedio(double precoMedio) {
		this.precoMedio = precoMedio;
	}
	public double getPrecoMedio() {
		return precoMedio;
	}
	
	//construtor padrao
	public Produto(Opcoes ops, int quant, double precoMedio) {
		super();
		this.ops = ops;
		this.quant = quant;
		this.precoMedio = precoMedio;
	}
	
	//metodo de verificar disponibilidade de um produto
	// se retorna 1 tem disponivel
	// se retorna -1 nao tem disponivel
	public int disponibilidade(int qtd) {
		if(qtd <= this.quant)
			return 1;
		else return -1;
	}
	
	//qnd a loja compra algum priduto aumenta o estoque
	public Produto aumentaEstoque(int qtd) {
		Produto retorna;
		if(qtd > 0) {
			int novaQuant = this.quant + qtd;
			retorna = new Produto(ops, novaQuant, precoMedio);
		} else
			retorna = this;
		return retorna;
	}
	
	//qnd a loja vende algum produto dimminui o estoque
	public Produto diminuiEstoque(int qtd) {
		Produto retorna;
		if(qtd <= this.quant) {
			int novaQuant = this.quant - qtd;
			retorna = new Produto(ops, novaQuant, precoMedio);
		} else
			retorna = this;
		return retorna;
	}
	
	public String toString() {
		return "Temos " + quant + " unidade(s) de " + ops.toString() + "a um preco medio de " + precoMedio;
	}
	
	

}
