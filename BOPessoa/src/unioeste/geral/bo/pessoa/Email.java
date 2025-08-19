package unioeste.geral.bo.pessoa;

import java.io.Serializable;

public class Email implements Serializable {
	public final static long serialVersionUID = 1;
	
	private String endereco_email;

	public String getEnderecoEmail() {
		return endereco_email;
	}
	public void setEnderecoEmail(String endereco_email) {
		this.endereco_email = endereco_email;
	}

}

