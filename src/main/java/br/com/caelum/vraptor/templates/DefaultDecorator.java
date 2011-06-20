package br.com.caelum.vraptor.templates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.Localization;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class DefaultDecorator implements TemplateDecorator {
	
	private final HttpServletRequest request;
	private final HttpSession session;
	private final Localization localization;
	private final Validator validator;
	
	public DefaultDecorator(HttpServletRequest request, HttpSession session,
			Localization localization, Validator validator) {
		this.request = request;
		this.session = session;
		this.localization = localization;
		this.validator = validator;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpSession getSession() {
		return session;
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
	}

}
