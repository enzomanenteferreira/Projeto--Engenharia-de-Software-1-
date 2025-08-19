package es1backendservicos;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import unioeste.geral.bo.endereco.Cidade;
import unioeste.geral.endereco.manager.UCEnderecoGeralServicos;

// Define o mapeamento para o endpoint "/obter-cidade"
@WebServlet("/obter-cidade")
public class ObterCidade extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ObterCidade() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        JSONObject jsonObject = new JSONObject();
        
        try {
            if (id == null || id.isBlank()) { 
                // Caso 1: Retornar todas as cidades
                List<Cidade> cidades = UCEnderecoGeralServicos.obterTodasCidades();
                JSONArray cidadesArray = new JSONArray();

                for (Cidade cidade : cidades) {
                    JSONObject cidadeJson = new JSONObject();
                    adicionarCidadeAoJSON(cidade, cidadeJson);
                    cidadesArray.put(cidadeJson);
                }
                
                jsonObject.put("cidades", cidadesArray);
            } else {
                // Caso 2: Retornar cidade específica por ID
                int cidadeId = Integer.parseInt(id);
                Cidade cidade = UCEnderecoGeralServicos.obterCidade(cidadeId);
                
                if (cidade == null) {
                    throw new Exception("Cidade não encontrada com o ID: " + cidadeId);
                }
                
                adicionarCidadeAoJSON(cidade, jsonObject);
            }
        } catch (NumberFormatException e) {
            jsonObject.put("erro", "ID inválido: " + id);
        } catch (Exception e) {
            jsonObject.put("erro", e.getMessage());
            e.printStackTrace(); // Para depuração
        }

        // Configurar o retorno como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
    }

    // Método auxiliar para adicionar dados da cidade ao JSON
    private void adicionarCidadeAoJSON(Cidade cidade, JSONObject json) throws Exception {
        json.put("sigla estado", cidade.getEstado().getSigla());
        json.put("nome estado", cidade.getEstado().getNome());
        json.put("id cidade", cidade.getId());
        json.put("nome cidade", cidade.getNome());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
