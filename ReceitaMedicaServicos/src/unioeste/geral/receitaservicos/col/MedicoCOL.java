package unioeste.geral.receitaservicos.col;

import java.sql.Connection;
import unioeste.geral.bo.receitamedicobo.Medico;
import unioeste.geral.receitaservicos.dao.MedicoDAO;

public class MedicoCOL {

    /**
     * Verifica se o ID do médico é válido (ex: maior que zero).
     */
    public static boolean idMedicoValido(int idMedico) {
        return idMedico > 0;
    }

    /**
     * Verifica se o CRM do médico é válido (apenas um exemplo simplificado).
     */
    public static boolean crmValido(String crm) {
        if (crm == null) return false;
        // Exemplo: exigimos ao menos 4 caracteres, até 15
        return crm.length() >= 4 && crm.length() <= 15;
    }

    /**
     * Verifica se um médico existe no banco, dado o idMedico.
     */
    public static boolean medicoExiste(int idMedico, Connection conn) throws Exception {
        Medico medico = MedicoDAO.selectMedicoByID(idMedico, conn);
        return (medico != null);
    }
}
