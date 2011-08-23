package br.com.caelum.vraptor.templates.plugins;

import br.com.caelum.vraptor.validator.Validations;

public class CustomValidations extends Validations {

	public String getText(String key) {
	    return super.i18n(key);
	}  
	
}
