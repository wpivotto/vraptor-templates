package br.com.caelum.vraptor.templates;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

@Component
@RequestScoped
public class DefaultService implements TemplateService {

	private final HttpServletResponse response;
	private final Result result;
	private final TemplatePlugin plugin;
	
	public DefaultService(Result result, HttpServletResponse response, TemplatePlugin plugin) {
		this.result = result;
		this.response = response;
		this.plugin = plugin;
	}
	
	@Override
	public Template use(String name) throws IOException {
		
		TemplateRenderer renderer = plugin.getRenderer(name);
		
		return new DefaultTemplate(renderer, response, result);
		
	}
	

}
