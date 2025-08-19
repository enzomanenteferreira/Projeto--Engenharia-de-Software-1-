package unioeste.geral.receitaservicos.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import unioeste.geral.bo.endereco.Endereco;
import unioeste.geral.bo.endereco.EnderecoEspecifico;
import unioeste.geral.bo.receitamedicobo.Medico;
import unioeste.geral.endereco.manager.UCEnderecoGeralServicos;

/**
 * Exemplo básico de DAO para a tabela 'medico'.
 * Ajuste de acordo com suas necessidades (telefones, emails etc.).
 */
public class MedicoDAO {

    /**
     * Consulta um médico pelo ID.
     */
    public static Medico selectMedicoByID(int idMedico, Connection conn) throws Exception {
        String sql = "SELECT * FROM medico WHERE id_medico = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idMedico);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Medico medico = new Medico();
            medico.setIdMedico(rs.getInt("id_medico"));
            medico.setPrimeiroNome(rs.getString("primeiro_nome_medico"));
            medico.setSobrenome(rs.getString("sobrenome_medico"));
            medico.setCPF(rs.getString("cpf_medico"));
            medico.setCrmMedico(rs.getString("crm_medico"));

            // Endereço
            int idEndereco = rs.getInt("id_endereco");
            Endereco endereco = UCEnderecoGeralServicos.obterEnderecoPorID(idEndereco); 
            // Supondo que você tenha esse método pronto no manager de Endereço
            EnderecoEspecifico endEsp = new EnderecoEspecifico();
            endEsp.setEndereco(endereco);
            endEsp.setNumero(rs.getInt("numero_endereco"));
            endEsp.setComplemento(rs.getString("complemento_endereco"));
            medico.setEnderecoResidencial(endEsp);

            rs.close();
            ps.close();
            return medico;
        }

        rs.close();
        ps.close();
        return null;
    }

    /**
     * Insere um novo médico na tabela.
     * Retorna o id_medico gerado (auto-increment).
     */
    public static int insertMedico(Medico medico, Connection conn) throws Exception {
        String sql = "INSERT INTO medico (primeiro_nome_medico, sobrenome_medico, cpf_medico, crm_medico, "
                   + "id_endereco, numero_endereco, complemento_endereco) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, medico.getPrimeiroNome());
        ps.setString(2, medico.getCrmMedico());
        ps.setString(3, medico.getCPF());
        ps.setString(4, medico.getCrmMedico());
        ps.setInt(5, medico.getEnderecoResidencial().getEndereco().getId());
        ps.setInt(6, medico.getEnderecoResidencial().getNumero());
        ps.setString(7, medico.getEnderecoResidencial().getComplemento());
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
