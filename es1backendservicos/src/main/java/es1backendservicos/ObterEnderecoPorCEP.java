package es1backendservicos;

import java.io.IOException;

import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import unioeste.geral.bo.endereco.Endereco;
import unioeste.geral.endereco.manager.UCEnderecoGeralServicos;

// Define o mapeamento para o endpoint "/obter-endereco-por-cep"
@WebServlet("/obter-endereco-por-cep")
public class ObterEnderecoPorCEP extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ObterEnderecoPorCEP() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cep = request.getParameter("cep");
        JSONObject jsonObject = new JSONObject();

        try {
            if (cep == null || cep.isBlank()) {
                throw new IllegalArgumentException("CEP não informado");
            }

            Endereco endereco = UCEnderecoGeralServicos.obterEnderecoPorCEP(cep);
            if (endereco == null) {
                throw new Exception("Endereço não encontrado para o CEP: " + cep);
            }

            jsonObject.put("id", endereco.getId());
            jsonObject.put("cep", endereco.getCEP());
            jsonObject.put("sigla estado", endereco.getCidade().getEstado().getSigla());
            jsonObject.put("nome estado", endereco.getCidade().getEstado().getNome());
            jsonObject.put("id cidade", endereco.getCidade().getId());
            jsonObject.put("nome cidade", endereco.getCidade().getNome());
            jsonObject.put("id bairro", endereco.getBairro().getId());
            jsonObject.put("nome bairro", endereco.getBairro().getNome());
            jsonObject.put("id logradouro", endereco.getLogradouro().getId());
            jsonObject.put("nome logradouro", endereco.getLogradouro().getNome());
            jsonObject.put("tipo logradouro", endereco.getLogradouro().getTipo_logradouro().getNome());

        } catch (IllegalArgumentException e) {
            jsonObject.put("erro", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            jsonObject.put("erro", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace(); // Para depuração
        }

        // Configurar a resposta para JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
