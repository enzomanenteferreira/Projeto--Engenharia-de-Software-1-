package unioeste.geral.receitaservicos.col;

import java.sql.Connection;
import unioeste.geral.bo.receitamedicobo.Receita;
import unioeste.geral.receitaservicos.dao.ReceitaDAO;

public class ReceitaCOL {

    /**
     * Verifica se o número da receita é válido (ex: maior que zero).
     */
    public static boolean nroReceitaValido(int nroReceita) {
        return nroReceita > 0;
    }

    /**
     * Verifica se uma receita existe no banco, dado o nroReceita.
     */
    public static boolean receitaExiste(int nroReceita, Connection conn) throws Exception {
        Receita r = ReceitaDAO.selectReceita(nroReceita, conn);
        return (r != null);
    }

    /**
     * Exemplo de verificação de data.
     */
    // public static boolean dataReceitaValida(Date data) {
    //     if (data == null) return false;
    //     // Exemplo: não pode ser futura demais, etc.
    //     return true;
    // }
}
