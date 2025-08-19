package es1backendservicos;

import java.io.BufferedReader;
import java.io.IOException;
import org.json.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import paciente.manager.UCPacienteServicos;
import unioeste.bo.paciente.Paciente;
import unioeste.geral.bo.endereco.Endereco;
import unioeste.geral.bo.endereco.EnderecoEspecifico;
import unioeste.geral.endereco.manager.UCEnderecoGeralServicos;

@WebServlet("/cadastrar-paciente")
public class CadastrarPaciente extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CadastrarPaciente() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lê o corpo da requisição e converte para JSONObject
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject jsonBody = new JSONObject(sb.toString());

        // Extrai os parâmetros do JSON
        String nome_paciente = jsonBody.optString("nome_paciente");
        String cpf = jsonBody.optString("cpf");
        String rg = jsonBody.optString("rg");
        String numero_endereco = jsonBody.optString("numero_endereco");
        String complemento_endereco = jsonBody.optString("complemento_endereco");
        String data_nascimento_paciente = jsonBody.optString("data_nascimento_paciente");
        String cep = jsonBody.optString("cep"); 
        
        Paciente paciente = new Paciente();
        EnderecoEspecifico enderecoResidencial = new EnderecoEspecifico();

        // Obtém o endereço a partir do CEP
        try {
            Endereco endereco = UCEnderecoGeralServicos.obterEnderecoPorCEP(cep);
            enderecoResidencial.setEndereco(endereco);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("erro", "CEP inválido ou não encontrado: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
            return;
        }

        // Prepara os dados do paciente
        paciente.setNome(nome_paciente.strip().toUpperCase());
        paciente.setCPF(cpf.strip());
        paciente.setRG(rg.strip());
        
        int numeroEndereco = 0;
        if (numero_endereco != null && !numero_endereco.isBlank()) {
            numeroEndereco = Integer.parseInt(numero_endereco);
        }
        enderecoResidencial.setNumero(numeroEndereco);
        enderecoResidencial.setComplemento(complemento_endereco);
        paciente.setEnderecoResidencial(enderecoResidencial);

        if (data_nascimento_paciente != null && !data_nascimento_paciente.isBlank()) {
            java.sql.Date dataNascimento = java.sql.Date.valueOf(data_nascimento_paciente);
            paciente.setDataNascimento(dataNascimento);
        }

        // Processa o cadastro do paciente
        JSONObject jsonObject = new JSONObject();
        try {
            UCPacienteServicos.cadastrarPaciente(paciente);
            jsonObject.put("inserido", true);
        } catch (Exception e) {
            jsonObject.put("erro", e.getMessage());
            e.printStackTrace();
        }
        
        // Retorna a resposta em JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
    }
}
