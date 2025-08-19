package unioeste.geral.bo.receitamedicobo;


import java.sql.Date;
import java.util.List;

import unioeste.bo.paciente.Paciente;
import unioeste.geral.bo.receitamedicobo.CID;
import unioeste.geral.bo.receitamedicobo.Medicamento;
import unioeste.geral.bo.receitamedicobo.Medico;

public class Receita {
    private int nroReceita;
    private Paciente paciente;
    private Medico medico;
    private Date dataReceita;
    private CID cid;
    private List<ReceitaMedicamento> medicamentos; // Relação 1-N com ReceitaMedicamento

    public int getNroReceita() {
        return nroReceita;
    }

    public void setNroReceita(int nroReceita) {
        this.nroReceita = nroReceita;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Date getDataReceita() {
        return dataReceita;
    }

    public void setDataReceita(Date dataReceita) {
        this.dataReceita = dataReceita;
    }

    public CID getCid() {
        return cid;
    }

    public void setCid(CID cid) {
        this.cid = cid;
    }

    public List<ReceitaMedicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<ReceitaMedicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }
}
