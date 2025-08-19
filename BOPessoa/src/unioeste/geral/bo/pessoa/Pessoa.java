package unioeste.geral.bo.pessoa;

import java.io.Serializable;
import java.util.ArrayList;

import unioeste.geral.bo.endereco.EnderecoEspecifico;

public class Pessoa implements Serializable{
	public final static long serialVersionUID = 1;
	
	private String nome;
	private String nome_social;
	private ArrayList <Fone> fones;
	private ArrayList <Email> emails;
	private EnderecoEspecifico end_especifico;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNomeSocial() {
		return nome_social;
	}
	public void setNomeSocial(String nome_social) {
		this.nome_social = nome_social;
	}
	public ArrayList <Fone> getFones() {
		return fones;
	}
	public void setFones(ArrayList <Fone> fones) {
		this.fones = fones;
	}
	public ArrayList <Email> getEmails() {
		return emails;
	}
	public void setEmails(ArrayList <Email> emails) {
		this.emails = emails;
	}
	public EnderecoEspecifico getEnd_especifico() {
		return end_especifico;
	}
	public void setEnd_especifico(EnderecoEspecifico end_especifico) {
		this.end_especifico = end_especifico;
	}
}
