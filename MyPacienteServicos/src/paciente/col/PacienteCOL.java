package paciente.col;

import java.sql.Connection;

import paciente.dao.PacienteDAO;
import paciente.manager.UCPacienteServicos;
import unioeste.bo.paciente.Paciente;

public class PacienteCOL {
	public static boolean rgValido(String RG) {
		if(RG==null) return false;
		return RG.matches("[0-9]+") && RG.length()>=7 && RG.length()<=11;
	}
	
	public static boolean cpfValido(String CPF) {
		if(CPF==null) return false;
		return CPF.matches("[0-9]+") && CPF.length()==11;
	}
	
	public static boolean pacienteCadastradoCPF(String CPF, Connection conexao) throws Exception {
	    if(PacienteDAO.selectPacienteCPF(CPF, conexao)!=null) return true;
	    return false;
	}

	
	public static boolean pacienteCadastradoRG(String RG) throws Exception {
		if(PacienteDAO.selectPacienteRG(RG)!=null) return true;
		return false;
	}
}
