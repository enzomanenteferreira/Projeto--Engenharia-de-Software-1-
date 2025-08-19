package unioeste.geral.receitaservicos.col;

import java.sql.Connection;
import unioeste.geral.bo.receitamedicobo.Medicamento;
import unioeste.geral.receitaservicos.dao.MedicamentoDAO;

public class MedicamentoCOL {

    /**
     * Verifica se o ID do medicamento é válido (ex: maior que zero).
     */
    public static boolean idMedicamentoValido(int idMedicamento) {
        return idMedicamento > 0;
    }

    /**
     * Verifica se o nome do medicamento é válido (ex: tamanho mínimo de 2, máximo de 200).
     */
    public static boolean nomeMedicamentoValido(String nomeMedicamento) {
        if (nomeMedicamento == null) return false;
        return nomeMedicamento.length() >= 2 && nomeMedicamento.length() <= 200;
    }

    /**
     * Verifica se um medicamento existe no banco, dado o idMedicamento.
     */
    public static boolean medicamentoExiste(int idMedicamento, Connection conn) throws Exception {
        Medicamento med = MedicamentoDAO.selectMedicamentoByID(idMedicamento, conn);
        return (med != null);
    }
}
