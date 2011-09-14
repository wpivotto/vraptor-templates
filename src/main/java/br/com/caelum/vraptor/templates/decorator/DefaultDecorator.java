package br.com.caelum.vraptor.templates.decorator;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.templates.Template;
import br.com.caelum.vraptor.templates.plugins.CustomValidations;

@Component
public class DefaultDecorator implements TemplateDecorator {
	
	private final Result result;
	private final HttpServletRequest request;
	
	public DefaultDecorator(Result result, HttpServletRequest request) {
		this.result = result;
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}
	
	public void decorate(Template template){
		includeRequestParameters(template);
		template.with("request", getRequest());
		template.with("contextPath", getRequest().getServletContext().getContextPath());
		template.with("i18n", new CustomValidations());
	}
	
	private void includeRequestParameters(Template template){
		for(Map.Entry<String, Object> entry : result.included().entrySet()){
			template.with(entry.getKey(), entry.getValue());
		}
	}

}
