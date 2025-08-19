package es1backendservicos;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import unioeste.bo.paciente.Paciente;
import unioeste.geral.bo.receitamedicobo.*;

import unioeste.geral.receitaservicos.manager.UCReceitaGeralServicos;
// Você deve ter services para buscar Paciente/Medico/CID/Medicamento, por ex.:
// import paciente.manager.UCPacienteServicos;
// import unioeste.geral.cid.manager.UCCIDServicos;
// import unioeste.geral.medicamento.manager.UCMedicamentoServicos;
// import unioeste.geral.medico.manager.UCMedicoServicos;

@WebServlet("/cadastrar-receita")
public class CadastrarReceitaMedica extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CadastrarReceitaMedica() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1) Ler JSON de entrada
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        try {
            JSONObject json = new JSONObject(sb.toString());

            // 2) Extrair dados da receita
            String rgPaciente = json.getString("rg_paciente");
            int idMedico = json.getInt("id_medico");
            String dataReceitaStr = json.getString("data_receita"); // "yyyy-mm-dd"
            int codCid = json.getInt("cod_cid");

            // 3) Montar objeto Receita
            Receita receita = new Receita();

            // Buscar paciente
            Paciente paciente = new Paciente();
            paciente.setRG(rgPaciente);
            // ou: Paciente paciente = UCPacienteServicos.consultarPacientePorRG(rgPaciente);
            receita.setPaciente(paciente);

            // Buscar medico
            Medico medico = new Medico();
            medico.setIdMedico(idMedico);
            // ou: Medico medico = UCMedicoServicos.consultarMedicoPorID(idMedico);
            receita.setMedico(medico);

            // Definir data da receita
            receita.setDataReceita(Date.valueOf(dataReceitaStr));

            // Buscar CID
            CID cid = new CID();
            cid.setCodCid(codCid);
            // ou: CID cid = UCCIDServicos.consultarCID(codCid);
            receita.setCid(cid);

            // 4) Extrair lista de medicamentos
            JSONArray arrMed = json.getJSONArray("medicamentos");
            List<ReceitaMedicamento> listaMed = new ArrayList<>();

            for (int i = 0; i < arrMed.length(); i++) {
                JSONObject medJson = arrMed.getJSONObject(i);

                int idMedicamento = medJson.getInt("id_medicamento");
                String dataInicio = medJson.getString("data_inicio"); // yyyy-mm-dd
                String dataFim = medJson.optString("data_fim", null); // pode não existir
                String posologia = medJson.getString("posologia");

                // Montar objeto ReceitaMedicamento
                ReceitaMedicamento rm = new ReceitaMedicamento();
                Medicamento medicamento = new Medicamento();
                medicamento.setIdMedicamento(idMedicamento);
                rm.setMedicamento(medicamento);

                rm.setDataInicio(Date.valueOf(dataInicio));
                if (dataFim != null && !dataFim.isBlank()) {
                    rm.setDataFim(Date.valueOf(dataFim));
                }
                rm.setPosologia(posologia);

                listaMed.add(rm);
            }

            receita.setMedicamentos(listaMed);

            // 5) Chamar UCReceitaGeralServicos para cadastrar
            UCReceitaGeralServicos.cadastrarReceita(receita);

            // 6) Retorno de sucesso
            JSONObject resposta = new JSONObject();
            resposta.put("inserido", true);
            resposta.put("nro_receita", receita.getNroReceita());

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(resposta.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JSONObject erro = new JSONObject();
            erro.put("erro", e.getMessage());
            response.getWriter().write(erro.toString());
        }
    }
}
