package unioeste.geral.bo.receitamedicobo;


import java.sql.Date;

import unioeste.geral.bo.receitamedicobo.Medicamento;

public class ReceitaMedicamento {
    private int idReceitaMedicamento;
    private int nroReceita; // chave estrangeira para Receita
    private Medicamento medicamento;
    private Date dataInicio;
    private Date dataFim;
    private String posologia;

    public int getIdReceitaMedicamento() {
        return idReceitaMedicamento;
    }

    public void setIdReceitaMedicamento(int idReceitaMedicamento) {
        this.idReceitaMedicamento = idReceitaMedicamento;
    }

    public int getNroReceita() {
        return nroReceita;
    }

    public void setNroReceita(int nroReceita) {
        this.nroReceita = nroReceita;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getPosologia() {
        return posologia;
    }

    public void setPosologia(String posologia) {
        this.posologia = posologia;
    }
}
