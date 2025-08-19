package es1backendservicos;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import unioeste.geral.bo.endereco.Bairro;
import unioeste.geral.bo.endereco.Cidade;
import unioeste.geral.bo.endereco.Endereco;
import unioeste.geral.bo.endereco.Estado;
import unioeste.geral.bo.endereco.Logradouro;
import unioeste.geral.bo.endereco.TipoLogradouro;
import unioeste.geral.endereco.manager.UCEnderecoGeralServicos;

// Define o mapeamento do endpoint para "/cadastrar-endereco"
@WebServlet("/cadastrar-endereco")
public class CadastrarEndereco extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public CadastrarEndereco() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Você pode implementar um GET se necessário
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lê o corpo da requisição JSON
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        String jsonBody = sb.toString();
        JSONObject jsonObject = new JSONObject(jsonBody);

        System.out.println(jsonBody);
        Endereco endereco = new Endereco();
        endereco.setCEP(jsonObject.getString("cep"));

        // Obtem os IDs do JSON
        Cidade cidade = new Cidade();
        cidade.setId(jsonObject.getInt("id_cidade"));
        endereco.setCidade(cidade);

        Bairro bairro = new Bairro();
        bairro.setId(jsonObject.getInt("id_bairro"));
        endereco.setBairro(bairro);

        Logradouro logradouro = new Logradouro();
        logradouro.setId(jsonObject.getInt("id_logradouro"));
        endereco.setLogradouro(logradouro);

        JSONObject respostaJson = new JSONObject();

        System.out.println(respostaJson);
        try {
            UCEnderecoGeralServicos.cadastrarEndereco(endereco);
            respostaJson.put("inserido", true);
        } catch (Exception e) {
            respostaJson.put("erro", true);
            e.printStackTrace();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respostaJson.toString());
    }
}
