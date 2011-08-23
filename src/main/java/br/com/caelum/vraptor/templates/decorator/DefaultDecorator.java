package br.com.caelum.vraptor.templates.decorator;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.Localization;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.templates.Template;
import br.com.caelum.vraptor.templates.plugins.CustomValidations;

@Component
public class DefaultDecorator implements TemplateDecorator {
	
	private final HttpServletRequest request;
	private final Localization localization;
	private final Validator validator;
	
	public DefaultDecorator(HttpServletRequest request, Localization localization, Validator validator) {
		this.request = request;
		this.localization = localization;
		this.validator = validator;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public Localization getLocalization() {
		return localization;
	}

	public Validator getValidator() {
		return validator;
	}
	
	public void decorate(Template template){
		template.with("request", getRequest());
		template.with("contextPath", getRequest().getServletContext().getContextPath());
		template.with("validator", getValidator());
		template.with("localization", getLocalization());
		template.with("i18n", new CustomValidations());
	}

}
