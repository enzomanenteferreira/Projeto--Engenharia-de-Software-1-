package unioeste.geral.bo.endereco;

import java.io.Serializable;

public class Cidade implements Serializable{
	public final static long serialVersionUID = 1;
	
	private String nome;
	private Estado estado;
	private int id;

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
