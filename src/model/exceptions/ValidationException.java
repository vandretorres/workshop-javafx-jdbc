package model.exceptions;

import java.util.HashMap;
import java.util.Map;

/*
 * Classe personalizada para permitir lan�ar exce��o quando campos de formul�rios n�o forem preenchidos de acordo
 * com nossa regra de valida��o
 * 
 * ValidationException extende de RunTimeException, assim permite utilizar o comando Throw
 */
public class ValidationException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;
	
	
	/*
	 * Lista do tipo Map abriga um par de chave/valor.
	 * Ser� utiolizado para armazenar os erros dos campos do formul�rio que utiliza esta classe
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
