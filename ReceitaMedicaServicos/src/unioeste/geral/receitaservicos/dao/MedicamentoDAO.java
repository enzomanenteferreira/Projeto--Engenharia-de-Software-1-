package unioeste.geral.receitaservicos.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import unioeste.geral.bo.receitamedicobo.Medicamento;

public class MedicamentoDAO {

    /**
     * Consulta um medicamento pelo ID.
     */
    public static Medicamento selectMedicamentoByID(int idMedicamento, Connection conn) throws Exception {
        String sql = "SELECT * FROM medicamento WHERE id_medicamento = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idMedicamento);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Medicamento med = new Medicamento();
            med.setIdMedicamento(rs.getInt("id_medicamento"));
            med.setNomeMedicamento(rs.getString("nome_medicamento"));
            rs.close();
            ps.close();
            return med;
        }

        rs.close();
        ps.close();
        return null;
    }

    /**
     * Insere um novo medicamento na tabela.
     * Retorna o id_medicamento (auto-increment) gerado.
     */
    public static int insertMedicamento(Medicamento med, Connection conn) throws Exception {
        String sql = "INSERT INTO medicamento (nome_medicamento) VALUES (?)";
        PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, med.getNomeMedicamento());
        ps.executeUpdate();

        // Recuperar a chave gerada
        ResultSet rs = ps.getGeneratedKeys();
        int idGerado = 0;
        if (rs.next()) {
            idGerado = rs.getInt(1);
        }

        rs.close();
        ps.close();
        return idGerado;
    }
}
