package unioeste.bo.paciente;

import java.io.Serializable;

public class Telefone implements Serializable {
	public final static long serialVersionUID = 1;
	
	private String numero;
	private int DDD;
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public int getDDD() {
		return DDD;
	}
	public void setDDD(int dDD) {
		DDD = dDD;
	}

}
