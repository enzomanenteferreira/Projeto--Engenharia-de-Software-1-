package unioeste.geral.bo.pessoa;

import java.io.Serializable;
import java.util.Date;


public class PessoaFisica extends Pessoa implements Serializable{
	public final static long serialVersionUID = 1;
	
	private String primeiro_nome;
	private String sobrenome;
	private String CPF;
	private Date data_nascimento;
	
	
	public String getPrimeiroNome() {
		return primeiro_nome;
	}
	public void setPrimeiroNome(String primeiro_nome) {
		this.primeiro_nome = primeiro_nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public String getCPF() {
		return CPF;
	}
	public void setCPF(String cPF) {
		CPF = cPF;
	}
	public Date getDataNascimento() {
		return data_nascimento;
	}
	public void setDataNascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	
}
