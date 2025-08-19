package unioeste.geral.receitaservicos.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import unioeste.geral.bo.receitamedicobo.CID;

public class CIDDAO {

    /**
     * Consulta um CID pelo código (cod_cid).
     */
    public static CID selectCIDById(int codCid, Connection conn) throws Exception {
        String sql = "SELECT * FROM cid WHERE cod_cid = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, codCid);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            CID cid = new CID();
            cid.setCodCid(rs.getInt("cod_cid"));
            cid.setNomeCid(rs.getString("nome_cid"));
            rs.close();
            ps.close();
            return cid;
        }

        rs.close();
        ps.close();
        return null;
    }

    /**
     * Insere um novo registro de CID na tabela.
     * Retorna o cod_cid (auto-increment) gerado.
     */
    public static int insertCID(CID cid, Connection conn) throws Exception {
        String sql = "INSERT INTO cid (nome_cid) VALUES (?)";
        PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, cid.getNomeCid());
        ps.executeUpdate();

        // Recuperar a chave gerada
        ResultSet rs = ps.getGeneratedKeys();
        int codGerado = 0;
        if (rs.next()) {
            codGerado = rs.getInt(1);
        }

        rs.close();
        ps.close();
        return codGerado;
    }
}
