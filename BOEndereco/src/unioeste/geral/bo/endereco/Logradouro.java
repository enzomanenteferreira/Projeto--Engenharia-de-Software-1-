package unioeste.geral.bo.endereco;

import java.io.Serializable;

public class Logradouro implements Serializable{
	public final static long serialVersionUID = 1;
	
	private String nome;
	private TipoLogradouro tipo_logradouro;
	private int id;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public TipoLogradouro getTipo_logradouro() {
		return tipo_logradouro;
	}
	public void setTipo_logradouro(TipoLogradouro tipo_logradouro) {
		this.tipo_logradouro = tipo_logradouro;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
