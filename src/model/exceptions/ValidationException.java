package model.exceptions;

import java.util.HashMap;
import java.util.Map;

/*
 * Classe personalizada para permitir lançar exceção quando campos de formulários não forem preenchidos de acordo
 * com nossa regra de validação
 * 
 * ValidationException extende de RunTimeException, assim permite utilizar o comando Throw
 */
public class ValidationException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;
	
	
	/*
	 * Lista do tipo Map abriga um par de chave/valor.
	 * Será utiolizado para armazenar os erros dos campos do formulário que utiliza esta classe
	 */
	public Map<String, String> errors = new HashMap<>();
	
	
	
	public ValidationException(String msg ) {
		
		super(msg);		
		
	}

	public Map<String, String> getErrors() {
		return errors;
	}
	
	public void addError(String fieldName, String errorMessage) {
		
		errors.put(fieldName, errorMessage);
		
	}
	
	

}
