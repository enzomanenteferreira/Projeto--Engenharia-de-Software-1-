package paciente.manager;

public class PacienteException extends Exception{
	private static final long serialVersionUID = 1L;

	public PacienteException(String errorMessage) {
	    super(errorMessage);
	}
}

