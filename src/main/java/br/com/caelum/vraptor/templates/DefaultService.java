package br.com.caelum.vraptor.templates;

import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.templates.plugins.TemplatePlugin;

@Component
public class DefaultService implements TemplateService {

	private final Result result;
	private final HttpServletResponse response;
	private final TemplatePlugin plugin;
	private final TemplateDecorator decorator;
	
	public DefaultService(Result result, HttpServletResponse response, TemplatePlugin plugin, TemplateDecorator decorator) {
		this.result = result;
		this.response = response;
		this.plugin = plugin;
		this.decorator = decorator;
	}
	
	@Override
	public Template use(String templateName){
		
		TemplateRenderer renderer = plugin.getRenderer(templateName);
		Template template = new DefaultTemplate(renderer, response, result);
		decorator.decorate(template);
		return template;
		
	}
	

}
