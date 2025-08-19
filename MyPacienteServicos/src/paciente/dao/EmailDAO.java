package paciente.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import unioeste.geral.bo.pessoa.Email;
import unioeste.apoio.banco.ConnectionBD;

public class EmailDAO {
	
	static ArrayList<Email> selectEmailsPacientes (String RG) throws Exception {
		ConnectionBD conexao = new ConnectionBD();
		Connection connection = conexao.getConnection();
		
		StringBuffer sql = new StringBuffer("SELECT email_paciente.endereco_email FROM email_paciente ");
		sql.append("WHERE email_paciente.RG = '").append(RG).append("';");

		
		ResultSet result = connection.createStatement().executeQuery(sql.toString());
		
		ArrayList<Email> emails = new ArrayList<Email>();
		
		while(result.next()) {
			Email email = new Email();
			email.setEnderecoEmail(result.getString("endereco_email"));
			emails.add(email);
		}
		
		return emails;
	}
	
	static void insertEmailPaciente(Email email, String RG) throws Exception {
		ConnectionBD conexao = new ConnectionBD();
		Connection connection = conexao.getConnection();
		
		StringBuffer sql = new StringBuffer("INSERT INTO email_paciente ");
		sql.append("(endereco_email, RG) VALUES ('");
		sql.append(email.getEnderecoEmail()).append("', ");
		sql.append("'").append(RG).append("');");
		
		connection.createStatement().executeUpdate(sql.toString());
	}
}
