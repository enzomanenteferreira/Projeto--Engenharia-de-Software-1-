package unioeste.geral.endereco.col;

import java.sql.Connection;

import unioeste.geral.bo.endereco.Bairro;
import unioeste.geral.endereco.dao.BairroDAO;

public class BairroCOL {
	public static boolean idValido(int id) {
		return id>0;
	}
	
	public static boolean bairroValido(Bairro bairro) {
		if(bairro==null) return false;
		if(!idValido(bairro.getId())) return false;
		if(bairro.getNome()==null) return false;
		return true;
	}
	
	public static boolean bairroCadastrado(Bairro bairro, Connection conexao) throws Exception {
		Bairro aux = BairroDAO.selectBairro(bairro.getId(), conexao);
		if(aux==null) return false;
		return true;
	}
}
