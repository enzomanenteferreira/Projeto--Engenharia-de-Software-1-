/**
 * 
 */
/**
 * 
 */
module MyPacienteServicos {
	requires BOPaciente;
	requires BOEndereco;
	requires MyEnderecoServicos;
	requires MyInfraAPI;
	requires BOPessoa;
	requires java.sql;
	exports paciente.col;
	exports paciente.dao;
	exports paciente.manager;
}