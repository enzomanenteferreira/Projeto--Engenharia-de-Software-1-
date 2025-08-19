package unioeste.geral.receitaservicos.col;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import unioeste.geral.bo.receitamedicobo.Medicamento;
import unioeste.geral.bo.receitamedicobo.ReceitaMedicamento;
import unioeste.geral.receitaservicos.dao.MedicamentoDAO;
import unioeste.geral.receitaservicos.dao.ReceitaMedicamentoDAO;

public class ReceitaMedicamentoCOL {

    /**
     * Verifica se as datas de início e fim são coerentes (fim >= início, etc.).
     */
    public static boolean dataInicioFimValida(Date dataInicio, Date dataFim) {
        if (dataInicio == null || dataFim == null) return false;
        // Exemplo: dataFim não pode ser antes de dataInicio
        return !dataFim.before(dataInicio);
    }

    /**
     * Verifica se a posologia é válida (ex: texto com tamanho mínimo e máximo).
     */
    public static boolean posologiaValida(String posologia) {
        if (posologia == null) return false;
        return posologia.length() >= 5 && posologia.length() <= 200;
    }

    /**
     * Exemplo de método para verificar se existe um registro em receita_medicamento,
     * caso fosse necessário implementar no DAO.
     */
    public static boolean receitaMedicamentoExiste(int idReceitaMedicamento, Connection conn) throws Exception {
        // Não há método no DAO para buscar por id_receita_medicamento diretamente,
        // mas, se houvesse, você poderia chamar algo como:
        // return (ReceitaMedicamentoDAO.selectById(idReceitaMedicamento, conn) != null);
        return false;
    }
}
