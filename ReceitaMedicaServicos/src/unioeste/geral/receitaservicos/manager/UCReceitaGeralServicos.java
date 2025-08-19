package unioeste.geral.receitaservicos.manager;


import java.sql.Connection;
import java.util.List;

import unioeste.apoio.banco.ConnectionBD;
import unioeste.geral.bo.receitamedicobo.*;
import unioeste.geral.receitaservicos.dao.ReceitaDAO;
import unioeste.geral.receitaservicos.dao.ReceitaMedicamentoDAO;

public class UCReceitaGeralServicos {

    /**
     * Cadastra uma nova receita e seus medicamentos na base de dados.
     */
    public static void cadastrarReceita(Receita receita) throws Exception {
        ConnectionBD conexaoBD = new ConnectionBD();
        Connection conn = conexaoBD.getConnection();

        try {
            conn.setAutoCommit(false);

            // 1) Inserir a receita
            int nroGerado = ReceitaDAO.insertReceita(receita, conn);
            receita.setNroReceita(nroGerado);

            // 2) Inserir os medicamentos relacionados
            if (receita.getMedicamentos() != null) {
                for (ReceitaMedicamento rm : receita.getMedicamentos()) {
                    rm.setNroReceita(nroGerado);
                    ReceitaMedicamentoDAO.insertReceitaMedicamento(rm, conn);
                }
            }

            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }

    /**
     * Consulta receita completa (com lista de medicamentos).
     */
    public static Receita consultarReceita(int nroReceita) throws Exception {
        ConnectionBD conexaoBD = new ConnectionBD();
        Connection conn = conexaoBD.getConnection();

        try {
            Receita receita = ReceitaDAO.selectReceita(nroReceita, conn);
            if (receita == null) {
                return null; // ou lançar exceção
            }

            // Carrega a lista de medicamentos
            List<ReceitaMedicamento> lista = ReceitaMedicamentoDAO.selectByNroReceita(nroReceita, conn);
            receita.setMedicamentos(lista);

            return receita;
        } finally {
            conn.close();
        }
    }
}
