package unioeste.geral.bo.endereco;

import java.io.Serializable;

public class EnderecoEspecifico implements Serializable{
	public final static long serialVersionUID = 1;
	
	private Endereco endereco;
	private int numero;
	private String complemento;
	
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
}
