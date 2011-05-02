package br.com.caelum.vraptor.templates;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

@Component
@RequestScoped
public class DefaultEngine implements TemplateEngine {

	private final HttpServletResponse response;
	private final Result result;
	private final TemplateConfiguration cfg;
	
	public DefaultEngine(Result result, HttpServletResponse response, TemplateConfiguration cfg) {
		this.result = result;
		this.response = response;
		this.cfg = cfg;
	}
	
	@Override
	public Template use(String name) throws IOException {
		
		TemplateRenderer renderer = cfg.getRenderer(name);
		
		return new DefaultTemplate(renderer, response, result);
		
	}
	

}
