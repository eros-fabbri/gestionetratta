package it.prova.gestionetratte.web.exception;

public class AirbusNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public AirbusNotFoundException(String string) {
		super(string);
	}
}
