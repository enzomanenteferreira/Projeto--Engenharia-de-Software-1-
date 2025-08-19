package unioeste.geral.receitaservicos.dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import unioeste.geral.bo.receitamedicobo.CID;
import unioeste.geral.bo.receitamedicobo.Medico;
import unioeste.bo.paciente.Paciente;
import unioeste.geral.bo.receitamedicobo.Receita;
import unioeste.apoio.banco.ConnectionBD;
import paciente.dao.PacienteDAO;
import unioeste.geral.receitaservicos.dao.*;
import unioeste.geral.receitaservicos.dao.MedicoDAO;

public class ReceitaDAO {

    /**
     * Insere uma nova receita na tabela 'receita' e retorna o número gerado (nro_receita).
     */
    public static int insertReceita(Receita receita, Connection conn) throws Exception {
        String sql = "INSERT INTO receita (rg_paciente, id_medico, data_receita, cod_cid) "
                   + "VALUES (?, ?, ?, ?)";
        
        PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, receita.getPaciente().getRG());
        ps.setInt(2, receita.getMedico().getIdMedico());
        ps.setDate(3, receita.getDataReceita()); // java.sql.Date
        ps.setInt(4, receita.getCid().getCodCid());
        
        ps.executeUpdate();
        
        // Recupera a chave gerada (nro_receita)
        ResultSet rs = ps.getGeneratedKeys();
        int nroReceitaGerado = 0;
        if (rs.next()) {
            nroReceitaGerado = rs.getInt(1);
        }
        rs.close();
        ps.close();
        
        return nroReceitaGerado;
    }

    /**
     * Seleciona uma receita pelo nro_receita e retorna objeto completo (exceto lista de medicamentos).
     * A lista de medicamentos deve ser buscada em ReceitaMedicamentoDAO.
     */
    public static Receita selectReceita(int nroReceita, Connection conn) throws Exception {
        String sql = "SELECT * FROM receita WHERE nro_receita = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, nroReceita);
        
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            Receita receita = new Receita();
            receita.setNroReceita(nroReceita);
            
            // Carrega paciente
            String rgPaciente = rs.getString("rg_paciente");
            Paciente paciente = PacienteDAO.selectPacienteRG(rgPaciente); 
            receita.setPaciente(paciente);
            
            // Carrega médico
            int idMedico = rs.getInt("id_medico");
            Medico medico = MedicoDAO.selectMedicoByID(idMedico, conn);
            receita.setMedico(medico);
            
            // Data da receita
            receita.setDataReceita(rs.getDate("data_receita"));
            
            // Carrega CID
            int codCid = rs.getInt("cod_cid");
            CID cid = CIDDAO.selectCIDById(codCid, conn);
            receita.setCid(cid);

            rs.close();
            ps.close();
            return receita;
        }
        
        rs.close();
        ps.close();
        return null;
    }
}
