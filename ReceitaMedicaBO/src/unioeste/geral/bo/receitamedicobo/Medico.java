package unioeste.geral.bo.receitamedicobo;

import unioeste.geral.bo.pessoa.PessoaFisica;
import unioeste.geral.bo.endereco.EnderecoEspecifico;

/**
 * A classe Medico herda de PessoaFisica e adiciona o idMedico (PK da tabela medico) 
 * e o CRM, além de qualquer outra informação específica de médico.
 */
public class Medico extends PessoaFisica {

    private static final long serialVersionUID = 1L; // se você utilizar Serializable

    private int idMedico;         // PK da tabela "medico"
    private String crmMedico;     // CRM (Conselho Regional de Medicina)
    
    // Caso queira guardar o endereço do médico aqui (como "endereço residencial"):
    private EnderecoEspecifico enderecoResidencial;

    // Construtor padrão
    public Medico() {
        super();
    }

    // Getters e Setters
    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public String getCrmMedico() {
        return crmMedico;
    }

    public void setCrmMedico(String crmMedico) {
        this.crmMedico = crmMedico;
    }

    public EnderecoEspecifico getEnderecoResidencial() {
        return enderecoResidencial;
    }

    public void setEnderecoResidencial(EnderecoEspecifico enderecoResidencial) {
        this.enderecoResidencial = enderecoResidencial;
    }
}
