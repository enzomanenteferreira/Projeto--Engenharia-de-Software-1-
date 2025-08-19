package unioeste.geral.receitaservicos.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import unioeste.geral.bo.receitamedicobo.Medicamento;
import unioeste.geral.bo.receitamedicobo.ReceitaMedicamento;
import unioeste.geral.receitaservicos.dao.*;

public class ReceitaMedicamentoDAO {

    public static void insertReceitaMedicamento(ReceitaMedicamento rm, Connection conn) throws Exception {
        String sql = "INSERT INTO receita_medicamento (nro_receita, id_medicamento, data_inicio, data_fim, posologia_medicamento) "
                   + "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, rm.getNroReceita());
        ps.setInt(2, rm.getMedicamento().getIdMedicamento());
        ps.setDate(3, rm.getDataInicio());
        ps.setDate(4, rm.getDataFim());
        ps.setString(5, rm.getPosologia());
        
        ps.executeUpdate();
        ps.close();
    }

    public static List<ReceitaMedicamento> selectByNroReceita(int nroReceita, Connection conn) throws Exception {
        String sql = "SELECT * FROM receita_medicamento WHERE nro_receita = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, nroReceita);
        
        ResultSet rs = ps.executeQuery();
        List<ReceitaMedicamento> lista = new ArrayList<>();
        
        while (rs.next()) {
            ReceitaMedicamento rm = new ReceitaMedicamento();
            rm.setIdReceitaMedicamento(rs.getInt("id_receita_medicamento"));
            rm.setNroReceita(nroReceita);
            
            // Carrega medicamento
            int idMedicamento = rs.getInt("id_medicamento");
            Medicamento med = MedicamentoDAO.selectMedicamentoByID(idMedicamento, conn);
            rm.setMedicamento(med);
            
            rm.setDataInicio(rs.getDate("data_inicio"));
            rm.setDataFim(rs.getDate("data_fim"));
            rm.setPosologia(rs.getString("posologia_medicamento"));
            
            lista.add(rm);
        }
        
        rs.close();
        ps.close();
        return lista;
    }
}
