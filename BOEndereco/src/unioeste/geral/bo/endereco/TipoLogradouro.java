package unioeste.geral.bo.endereco;

import java.io.Serializable;

public class TipoLogradouro implements Serializable{
	public final static long serialVersionUID = 1;
	
	private String nome;
	private int id;

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
