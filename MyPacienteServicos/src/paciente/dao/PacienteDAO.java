package paciente.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import unioeste.geral.bo.pessoa.Email;
import unioeste.apoio.banco.ConnectionBD;
import unioeste.bo.paciente.Paciente;
import unioeste.bo.paciente.Telefone;
import unioeste.geral.bo.endereco.Endereco;
import unioeste.geral.bo.endereco.EnderecoEspecifico;
import unioeste.geral.endereco.manager.UCEnderecoGeralServicos;



public class PacienteDAO {
	public static ArrayList<Paciente> selectPacientes(Connection connection) throws Exception{
		StringBuffer sql = new StringBuffer("SELECT * FROM paciente;");
		ResultSet result = connection.createStatement().executeQuery(sql.toString());
		
		ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
		
		while(result.next()) {
			Paciente paciente = new Paciente();
			paciente.setRG(result.getString("RG"));
			paciente.setCPF(result.getString("CPF_paciente"));
			String nome = result.getString("nome_paciente");
			paciente.setPrimeiroNome(nome.substring(0, nome.indexOf(' ')));
			paciente.setSobrenome(nome.substring(nome.indexOf(' ')+1));
			paciente.setNome(nome);
			paciente.setNomeSocial(nome);
			paciente.setDataNascimento(result.getDate("data_nascimento_paciente"));
			
			// Emails
			paciente.setEmails(EmailDAO.selectEmailsPacientes(paciente.getRG()));
			
			// Telefones 
			paciente.setTelefones(TelefoneDAO.selectTelefonesPacientes(paciente.getRG()));
			
			// Endereço
			Endereco endereco = UCEnderecoGeralServicos.obterEnderecoPorID(Integer.parseInt(result.getString("id_endereco")));
			EnderecoEspecifico end_especifico = new EnderecoEspecifico();
			end_especifico.setEndereco(endereco);
			end_especifico.setComplemento(result.getString("complemento_endereco"));
			end_especifico.setNumero(result.getInt("numero_endereco"));
			paciente.setEnderecoResidencial(end_especifico);
			
			pacientes.add(paciente);
		}
		
		return pacientes;
	}
	
	public static Paciente selectPacienteCPF(String CPF, Connection conexao) throws Exception {
		
		StringBuffer sql = new StringBuffer("SELECT * FROM paciente WHERE CPF_paciente = '");
		sql.append(CPF).append("';");
		//
		ResultSet result = conexao.createStatement().executeQuery(sql.toString());
		
		if(result.next()) {
			// Dados pessoais
			Paciente paciente = new Paciente();
			paciente.setRG(result.getString("RG"));
			paciente.setCPF(result.getString("CPF_paciente"));
			String nome = result.getString("nome_paciente");
			paciente.setPrimeiroNome(nome.substring(0, nome.indexOf(' ')));
			paciente.setSobrenome(nome.substring(nome.indexOf(' ')+1));
			paciente.setNome(nome);
			paciente.setNomeSocial(nome);
			paciente.setDataNascimento(result.getDate("data_nascimento_paciente"));
			
			// Emails
			paciente.setEmails(EmailDAO.selectEmailsPacientes(paciente.getRG()));
			
			// Telefones 
			paciente.setTelefones(TelefoneDAO.selectTelefonesPacientes(paciente.getRG()));
			
			// Endereço
			Endereco endereco = UCEnderecoGeralServicos.obterEnderecoPorID(Integer.parseInt(result.getString("id_endereco")));
			EnderecoEspecifico end_especifico = new EnderecoEspecifico();
			end_especifico.setEndereco(endereco);
			end_especifico.setComplemento(result.getString("complemento_endereco"));
			end_especifico.setNumero(result.getInt("numero_endereco"));
			paciente.setEnderecoResidencial(end_especifico);
			
			return paciente;
		}
		else return null;
	}
	
	public static Paciente selectPacienteRG (String RG) throws Exception {
	    ConnectionBD conexao1 = new ConnectionBD();
	    Connection connection = conexao1.getConnection();
	    
	    StringBuffer sql = new StringBuffer("SELECT * FROM paciente WHERE rg = '");
	    sql.append(RG).append("';");
	    //
	    ResultSet result = connection.createStatement().executeQuery(sql.toString());
	    
	    if(result.next()) {
	        // Dados pessoais
	        Paciente paciente = new Paciente();
	        paciente.setRG(result.getString("RG"));
	        paciente.setCPF(result.getString("CPF_paciente"));
	        String nome = result.getString("nome_paciente");
	        paciente.setPrimeiroNome(nome.substring(0, nome.indexOf(' ')));
	        paciente.setSobrenome(nome.substring(nome.indexOf(' ')+1));
	        paciente.setNome(nome);
	        paciente.setNomeSocial(nome);
	        paciente.setDataNascimento(result.getDate("data_nascimento_paciente"));
	        
	        // Emails
	        paciente.setEmails(EmailDAO.selectEmailsPacientes(RG));
	        
	        // Telefones 
	        paciente.setTelefones(TelefoneDAO.selectTelefonesPacientes(RG));
	        
	        // Endereço
	        Endereco endereco = UCEnderecoGeralServicos.obterEnderecoPorID(Integer.parseInt(result.getString("id_endereco")));
	        EnderecoEspecifico end_especifico = new EnderecoEspecifico();
	        end_especifico.setEndereco(endereco);
	        end_especifico.setComplemento(result.getString("complemento_endereco"));
	        end_especifico.setNumero(result.getInt("numero_endereco"));
	        paciente.setEnderecoResidencial(end_especifico);
	        
	        return paciente;
	    }
	    
	    return null;
	}
	
	public static void insertPaciente (Paciente paciente) throws Exception {
		ConnectionBD conexao1 = new ConnectionBD();
		Connection connection = conexao1.getConnection();
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		
		 EnderecoEspecifico end_residencial = paciente.getEnderecoResidencial();
		    int idEndereco = end_residencial.getEndereco().getId(); // Supondo que Endereco tenha getIdEndereco()
		

		    StringBuffer sql = new StringBuffer("INSERT INTO paciente ");
		    sql.append("(RG, CPF_paciente, nome_paciente, data_nascimento_paciente, ");
		    sql.append("id_endereco, numero_endereco, complemento_endereco) VALUES ('");
		    sql.append(paciente.getRG()).append("', ");
		    sql.append("'").append(paciente.getCPF()).append("', ");
		    sql.append("'").append(paciente.getNome()).append("', ");
		    sql.append("'").append(formato.format(paciente.getDataNascimento())).append("', ");
		    sql.append(idEndereco).append(", "); // ID do endereço
		    sql.append(end_residencial.getNumero()).append(", ");
		    sql.append("'").append(end_residencial.getComplemento()).append("'); ");

		    connection.createStatement().executeUpdate(sql.toString());
		
		    if (paciente.getEmails() != null) {
		        for (Email email : paciente.getEmails())
		            EmailDAO.insertEmailPaciente(email, paciente.getRG());
		    }
		    
		    if (paciente.getTelefones() != null) {
		        for (Telefone telefone : paciente.getTelefones())
		            TelefoneDAO.insertTelefonePaciente(telefone, paciente.getRG());
		    }
	}
	
	public static void main (String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o RG do contribuinte:");
        // 1111111111
        // 2222222222
        String rg = scanner.nextLine();

        Paciente c1 = PacienteDAO.selectPacienteRG(rg);

        if (c1 != null) {
            System.out.println("Informações do Contribuinte:");
            System.out.println("Nome: " + c1.getNome());
            System.out.println("CPF: " + c1.getCPF());
            System.out.println("Data de Nascimento: " + c1.getDataNascimento());

            EnderecoEspecifico endereco = c1.getEnderecoResidencial();
            System.out.println("\nEndereço:");
            System.out.println("CEP: " + endereco.getEndereco().getCEP());
            System.out.println("Cidade: " + endereco.getEndereco().getCidade().getNome());
            System.out.println("Rua: " + endereco.getEndereco().getLogradouro().getNome());
            System.out.println("Número: " + endereco.getNumero());
            System.out.println("Complemento: " + endereco.getComplemento());

            System.out.println("\nTelefones:");
            for (Telefone telefone : c1.getTelefones()) {
                System.out.println("Número: " + telefone.getNumero());
                System.out.println("DDD: " + telefone.getDDD());
                System.out.println("----------------------------------------");
            }

            System.out.println("\nEmails:");
            for (Email email : c1.getEmails()) {
                System.out.println("Endereço: " + email.getEnderecoEmail());
                System.out.println("----------------------------------------");
            }
            

            // Exiba outras informações conforme necessário
        } else {
            System.out.println("Contribuinte não encontrado.");
        }
        scanner.close();
	}
}