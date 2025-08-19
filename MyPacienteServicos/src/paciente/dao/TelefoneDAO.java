package paciente.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import unioeste.apoio.banco.ConnectionBD;
import unioeste.bo.paciente.Telefone;


public class TelefoneDAO {
	
	static ArrayList<Telefone> selectTelefonesPacientes (String RG) throws Exception {
		ConnectionBD conexao = new ConnectionBD();
		Connection connection = conexao.getConnection();
		
		StringBuffer sql = new StringBuffer("SELECT telefone_paciente.numero_telefone, telefone_paciente.numero_ddd ");
		sql.append("FROM telefone_paciente WHERE telefone_paciente.RG = '").append(RG).append("';");

		
		ResultSet result = connection.createStatement().executeQuery(sql.toString());
		
		ArrayList<Telefone> telefones = new ArrayList<Telefone>();
		
		while(result.next()) {
			Telefone telefone = new Telefone();
			telefone.setNumero(result.getString("numero_telefone"));
			telefone.setDDD(result.getInt("numero_ddd"));
			telefones.add(telefone);
		}
		
		return telefones;
	}
	
	static void insertTelefonePaciente(Telefone telefone, String RG) throws Exception {
	    Connection connection = ConnectionBD.getConnection();
	    
	    String sql = "INSERT INTO telefone_paciente (numero_telefone, numero_ddd, RG) VALUES (?, ?, ?)";
	    
	    PreparedStatement pstmt = connection.prepareStatement(sql);
	    pstmt.setString(1, telefone.getNumero());
	    pstmt.setInt(2, telefone.getDDD());
	    pstmt.setString(3, RG);
	    
	    pstmt.executeUpdate();
	    
	    pstmt.close();
	    connection.close(); // Feche a conexão para evitar vazamentos de recurso
	}

}
