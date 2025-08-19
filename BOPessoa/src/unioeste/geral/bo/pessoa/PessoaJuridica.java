package unioeste.geral.bo.pessoa;

import java.io.Serializable;

public class PessoaJuridica extends Pessoa implements Serializable {
	public final static long serialVersionUID = 1;
	
	private String CNPJ;

	public String getCNPJ() {
		return CNPJ;
	}

	public void setCNPJ(String cNPJ) {
		CNPJ = cNPJ;
	}
	
	
}
