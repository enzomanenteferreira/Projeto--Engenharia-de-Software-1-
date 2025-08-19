package unioeste.geral.endereco.col;

import java.sql.Connection;

import unioeste.geral.bo.endereco.Cidade;
import unioeste.geral.endereco.dao.CidadeDAO;


public class CidadeCOL {
	public static boolean idValido(int id) {
		return id>0;
	}
	
	public static boolean cidadeValida(Cidade cidade) {
		if(cidade==null) return false;
		if(!idValido(cidade.getId())) return false;
		if(cidade.getNome()==null) return false;
		if(!EstadoCOL.estadoValido(cidade.getEstado())) return false;
		
		return true;
	}
	
	public static boolean cidadeCadastrada(Cidade cidade, Connection conexao) throws Exception {
		Cidade aux = CidadeDAO.selectCidade(cidade.getId(), conexao);
		if(aux==null) return false;
		if(!aux.getEstado().getSigla().equals(cidade.getEstado().getSigla())) return false;
		
		return true;
	}
}
