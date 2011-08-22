package br.com.caelum.vraptor.templates;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.templates.decorator.TemplateDecorator;
import br.com.caelum.vraptor.templates.plugins.TemplatePlugin;
import br.com.caelum.vraptor.templates.plugins.TemplateRenderer;

@Component
public class DefaultService implements TemplateService {

	private final Result result;
	private final HttpServletResponse response;
	private final TemplatePlugin plugin;
	private final List<TemplateDecorator> decorators;
	
	public DefaultService(Result result, HttpServletResponse response, TemplatePlugin plugin, List<TemplateDecorator> decorators) {
		this.result = result;
		this.response = response;
		this.plugin = plugin;
		this.decorators = decorators;
	}
	
	@Override
	public Template use(String templateName){
		
		TemplateRenderer renderer = plugin.getRenderer(templateName);
		Template template = new DefaultTemplate(renderer, response, result);
		for(TemplateDecorator decorator : decorators){
			decorator.decorate(template);
		}
		return template;
		
	}
	

}
