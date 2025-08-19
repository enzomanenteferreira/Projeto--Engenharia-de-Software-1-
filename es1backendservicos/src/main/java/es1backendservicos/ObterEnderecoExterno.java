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

// Define o mapeamento para o endpoint "/obter-endereco"
@WebServlet("/obter-endereco")
public class ObterEnderecoExterno extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ObterEnderecoExterno() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cep = request.getParameter("cep");
        JSONObject jsonObject = new JSONObject();

        try {
            Endereco endereco = UCEnderecoGeralServicos.obterEnderecoExterno(cep);
            jsonObject.put("cep", endereco.getCEP());
            jsonObject.put("sigla estado", endereco.getCidade().getEstado().getSigla());
            jsonObject.put("nome cidade", endereco.getCidade().getNome());
            jsonObject.put("nome bairro", endereco.getBairro().getNome());
            jsonObject.put("nome logradouro", endereco.getLogradouro().getNome());
        } catch (Exception e) {
            jsonObject.put("erro", true);
            e.printStackTrace();
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
