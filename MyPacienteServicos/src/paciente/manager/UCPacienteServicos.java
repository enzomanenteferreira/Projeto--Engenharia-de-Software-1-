package paciente.manager;

import java.sql.Connection;
import java.util.ArrayList;

import unioeste.geral.bo.pessoa.Email;
import paciente.col.PacienteCOL;
import paciente.dao.EmailDAO;
import paciente.dao.PacienteDAO;
import paciente.dao.TelefoneDAO;
import unioeste.apoio.banco.ConnectionBD;
import unioeste.bo.paciente.Paciente;
import unioeste.bo.paciente.Telefone;
import unioeste.geral.endereco.manager.EnderecoException;

public class UCPacienteServicos {
	public static ArrayList<Paciente> obterPacientes () throws Exception{
		ConnectionBD conexao = new ConnectionBD();
		Connection connection = conexao.getConnection();
		ArrayList<Paciente> pacientes = PacienteDAO.selectPacientes(conexao.getConnection());
		
		if(pacientes==null) throw new PacienteException("Pacientes não cadastrados");
		connection.close();
		return pacientes;
	}
	
	public static Paciente obterPacienteCPF(String CPF) throws Exception {
	    ConnectionBD conexao = new ConnectionBD();
	    Connection connection = conexao.getConnection();
	    Paciente pacientes = PacienteDAO.selectPacienteCPF(CPF, conexao.getConnection());
	    
	    if(pacientes==null) throw new PacienteException("Paciente não cadastrado com esse CPF");
	    connection.close();
	    return pacientes;
	}
	
	public static void cadastrarPaciente(Paciente paciente) throws Exception {
	    Connection conexao = ConnectionBD.getConnection();
	    conexao.setAutoCommit(false);
	    
	    try {
	        if (!PacienteCOL.cpfValido(paciente.getCPF())) {
	            throw new Exception("CPF inválido");
	        }
	        
	        if (!PacienteCOL.rgValido(paciente.getRG())) {
	            throw new Exception("RG inválido");
	        }
	        
	        if (PacienteCOL.pacienteCadastradoCPF(paciente.getCPF(), conexao)) {
	            throw new Exception("Paciente já cadastrado");
	        }
	        
	        PacienteDAO.insertPaciente(paciente);
	        /* AJUSTAR AQUI
	         if (paciente.getEmails() != null) {
		        for (Email email : paciente.getEmails())
		            EmailDAO.insertEmailPaciente(email, paciente.getRG());
		    }
		    
		    if (paciente.getTelefones() != null) {
		        for (Telefone telefone : paciente.getTelefones())
		            TelefoneDAO.insertTelefonePaciente(telefone, paciente.getRG());
		    }
	         */
	        conexao.commit();
	    } catch(Exception e) {
	        conexao.rollback();
	        e.printStackTrace();
	        throw new Exception("Não foi possível cadastrar o paciente", e);
	    } finally {
	        conexao.close();
	    }
	}
	
}
