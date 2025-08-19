package unioeste.bo.paciente;

import java.io.Serializable;
import java.util.ArrayList;


import unioeste.geral.bo.pessoa.PessoaFisica;
import unioeste.geral.bo.endereco.EnderecoEspecifico;

public class Paciente extends PessoaFisica implements Serializable{
public final static long serialVersionUID = 1;
	
	private String RG;
	private EnderecoEspecifico endereco_residencial;
	private ArrayList <Telefone> telefones;
	private ArrayList <Email>   emails;
	
	public String getRG() {
		return RG;
	}
	public void setRG(String rG) {
		RG = rG;
	}
	
	public EnderecoEspecifico getEnderecoResidencial() {
		return endereco_residencial;
	}
	public void setEnderecoResidencial(EnderecoEspecifico endereco_residencial) {
		this.endereco_residencial = endereco_residencial;
	}
	public ArrayList <Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(ArrayList <Telefone> telefones) {
		this.telefones = telefones;
	}
	public ArrayList <Email> getEmail() {
		return emails;
	}
	public void setEmail(ArrayList <Email> emails) {
		this.emails = emails;
	}

}
