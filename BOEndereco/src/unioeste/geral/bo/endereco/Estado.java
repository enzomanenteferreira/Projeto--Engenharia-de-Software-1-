package unioeste.geral.bo.endereco;

import java.io.Serializable;

public class Estado implements Serializable{
	public final static long serialVersionUID = 1;
	
	private String sigla;
	private String nome;
	
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
