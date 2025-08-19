package unioeste.geral.bo.endereco;

import java.io.Serializable;

public class Endereco implements Serializable{
	public final static long serialVersionUID = 1;
	
	private Cidade cidade;
	private Bairro bairro;
	private Logradouro logradouro;
	private String CEP;
	private int id;
	
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public Bairro getBairro() {
		return bairro;
	}
	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}
	public Logradouro getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}
	public String getCEP() {
		return CEP;
	}
	public void setCEP(String cEP) {
		CEP = cEP;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
