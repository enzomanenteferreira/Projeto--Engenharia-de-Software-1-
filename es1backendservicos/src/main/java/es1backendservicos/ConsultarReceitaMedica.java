package es1backendservicos;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import unioeste.geral.bo.receitamedicobo.*;
import unioeste.geral.receitaservicos.manager.UCReceitaGeralServicos;

@WebServlet("/consultar-receita")
public class ConsultarReceitaMedica extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ConsultarReceitaMedica() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1) Obter parâmetro nro_receita
        String nroStr = request.getParameter("nro_receita");
        JSONObject json = new JSONObject();

        try {
            if (nroStr == null || nroStr.isBlank()) {
                throw new Exception("nro_receita não informado");
            }

            int nroReceita = Integer.parseInt(nroStr);

            // 2) Consultar receita
            Receita receita = UCReceitaGeralServicos.consultarReceita(nroReceita);
            if (receita == null) {
                json.put("erro", "Receita não encontrada para nro_receita = " + nroReceita);
            } else {
                // 3) Montar JSON de retorno
                json.put("nro_receita", receita.getNroReceita());
                json.put("data_receita", receita.getDataReceita().toString());
                json.put("rg_paciente", receita.getPaciente().getRG());
                // Se quiser mais dados do paciente, busque e inclua
                json.put("id_medico", receita.getMedico().getIdMedico());
                json.put("crm_medico", receita.getMedico().getCrmMedico());
                // etc.

                json.put("cod_cid", receita.getCid().getCodCid());
                json.put("nome_cid", receita.getCid().getNomeCid());

                // Lista de medicamentos
                JSONArray arrMeds = new JSONArray();
                if (receita.getMedicamentos() != null) {
                    for (ReceitaMedicamento rm : receita.getMedicamentos()) {
                        JSONObject jrm = new JSONObject();
                        jrm.put("id_receita_medicamento", rm.getIdReceitaMedicamento());
                        jrm.put("id_medicamento", rm.getMedicamento().getIdMedicamento());
                        jrm.put("nome_medicamento", rm.getMedicamento().getNomeMedicamento());
                        jrm.put("data_inicio", rm.getDataInicio() != null ? rm.getDataInicio().toString() : "");
                        jrm.put("data_fim", rm.getDataFim() != null ? rm.getDataFim().toString() : "");
                        jrm.put("posologia", rm.getPosologia());
                        arrMeds.put(jrm);
                    }
                }
                json.put("medicamentos", arrMeds);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            json.put("erro", e.getMessage());
        }

        // 4) Retornar JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }

    // Se quiser também aceitar POST, chame doGet(request, response)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
