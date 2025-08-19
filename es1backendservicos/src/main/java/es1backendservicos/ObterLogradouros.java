package es1backendservicos;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import unioeste.geral.bo.endereco.Logradouro;
import unioeste.geral.endereco.manager.UCEnderecoGeralServicos;

// Define o mapeamento para o endpoint "/obter-logradouros"
@WebServlet("/obter-logradouros")
public class ObterLogradouros extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject jsonResponse = new JSONObject();

        try {
            // Obtém todos os logradouros
            List<Logradouro> logradouros = UCEnderecoGeralServicos.obterTodosLogradouros();
            JSONArray logradourosArray = new JSONArray();

            // Converte os logradouros para JSON
            for (Logradouro log : logradouros) {
                JSONObject logradouroJson = new JSONObject();
                logradouroJson.put("id_logradouro", log.getId());
                logradouroJson.put("nome_logradouro", log.getNome());

                logradourosArray.put(logradouroJson);
            }

            jsonResponse.put("logradouros", logradourosArray);
        } catch (Exception e) {
            jsonResponse.put("erro", "Erro ao obter logradouros: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace(); // Para depuração
        }

        // Configura a resposta HTTP como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
