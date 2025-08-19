package es1backendservicos;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import unioeste.geral.bo.endereco.Bairro;
import unioeste.geral.endereco.manager.UCEnderecoGeralServicos;

// Define o mapeamento para o endpoint "/obter-bairros"
@WebServlet("/obter-bairros")
public class ObterBairros extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ObterBairros() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        JSONObject jsonObject = new JSONObject();

        try {  
            if (id == null || id.isBlank()) { 
                // Busca todos os bairros
                List<Bairro> bairros = UCEnderecoGeralServicos.obterTodosBairros();
                JSONArray bairrosArray = new JSONArray();

                for (Bairro bairro : bairros) {
                    JSONObject bairroJson = new JSONObject();
                    adicionarBairroAoJSON(bairro, bairroJson);
                    bairrosArray.put(bairroJson);
                }

                jsonObject.put("bairros", bairrosArray);
            } 
            // Se você quiser tratar casos de id específico, pode adicionar lógica aqui
        } catch (NumberFormatException e) {
            jsonObject.put("erro", "ID inválido: " + id);
        } catch (Exception e) {
            jsonObject.put("erro", e.getMessage());
            e.printStackTrace(); // Para debug
        }

        // Configurar o retorno como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
    }

    private void adicionarBairroAoJSON(Bairro bairro, JSONObject json) {
        json.put("id_bairro", bairro.getId());
        json.put("nome_bairro", bairro.getNome());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
