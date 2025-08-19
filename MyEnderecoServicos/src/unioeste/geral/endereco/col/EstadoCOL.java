package unioeste.geral.endereco.col;

import java.sql.Connection;

import unioeste.geral.bo.endereco.Estado;
import unioeste.geral.endereco.dao.EstadoDAO;

public class EstadoCOL {
	public static boolean siglaValida(String sigla) {
		return sigla.matches("[A-Z]+") && sigla.length()==2; // Verificação se esta dentro do alfabeto valido e até 2 de carac.
	}
	
	public static boolean estadoValido(Estado estado) {
		if(estado==null) return false;
		if(!siglaValida(estado.getSigla())) return false;
		if(estado.getNome()==null) return false;
		
		return true;
	}
	
	public static boolean estadoCadastrado(Estado estado, Connection conexao) throws Exception {
		Estado aux = EstadoDAO.selectEstado(estado.getSigla(), conexao);
		if(aux==null) return false;
		return true;
	}
}
