	package es1backendservicos;
	
	import jakarta.servlet.ServletException;
	import jakarta.servlet.annotation.WebServlet;
	import jakarta.servlet.http.HttpServlet;
	import jakarta.servlet.http.HttpServletRequest;
	import jakarta.servlet.http.HttpServletResponse;
	
	import paciente.manager.UCPacienteServicos;
	import unioeste.bo.paciente.Paciente;
	
	import java.io.IOException;
	import java.util.ArrayList;
	
	import org.json.JSONArray;
	import org.json.JSONObject;
	
	/**
	 * Servlet implementation class ObterPacientes
	 */
	@WebServlet("/obter-pacientes")
	public class ObterPacientes extends HttpServlet {
	    private static final long serialVersionUID = 1L;
	
	    public ObterPacientes() {
	        super();
	    }
	
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        JSONObject jobject = new JSONObject();
	
	        try {
	            // Busca todos os pacientes cadastrados
	            ArrayList<Paciente> pacientes = UCPacienteServicos.obterPacientes();
	            JSONArray jpacientes = new JSONArray();
	
	            // Converte os pacientes para JSON
	            for (Paciente paciente : pacientes) {
	                JSONObject jpaciente = new JSONObject();
	                jpaciente.put("cpf", paciente.getCPF());
	                jpaciente.put("rg", paciente.getRG());
	                jpaciente.put("nome", paciente.getNome());
	                jpacientes.put(jpaciente);
	            }
	
	            jobject.put("pacientes", jpacientes);
	        } catch (Exception e) {
	            jobject.put("erro", "Erro ao obter pacientes: " + e.getMessage());
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            e.printStackTrace(); // Para depuração
	        }
	
	        // Configura a resposta como JSON
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	
	        response.getWriter().write(jobject.toString());
	    }
	
	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }
	}
