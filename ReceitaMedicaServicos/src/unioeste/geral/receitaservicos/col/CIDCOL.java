package unioeste.geral.receitaservicos.col;

import java.sql.Connection;
import unioeste.geral.bo.receitamedicobo.CID;
import unioeste.geral.receitaservicos.dao.CIDDAO;

public class CIDCOL {

    /**
     * Verifica se o código do CID (codCid) é válido (ex: maior que zero).
     */
    public static boolean codCidValido(int codCid) {
        return codCid > 0;
    }

    /**
     * Verifica se o nome do CID é válido (ex: tamanho mínimo de 2, máximo de 100).
     */
    public static boolean nomeCidValido(String nomeCid) {
        if (nomeCid == null) return false;
        return nomeCid.length() >= 2 && nomeCid.length() <= 100;
    }

    /**
     * Verifica se um CID existe no banco, dado o codCid.
     */
    public static boolean cidExiste(int codCid, Connection conn) throws Exception {
        CID cid = CIDDAO.selectCIDById(codCid, conn);
        return (cid != null);
    }
}
