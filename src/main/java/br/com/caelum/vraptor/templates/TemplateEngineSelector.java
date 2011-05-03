package br.com.caelum.vraptor.templates;

import javax.servlet.ServletContext;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;
import br.com.caelum.vraptor.templates.freemarker.FreemarkerConfiguration;
import br.com.caelum.vraptor.templates.velocity.VelocityConfiguration;

@Component
@ApplicationScoped
public class TemplateEngineSelector implements ComponentFactory<TemplateConfiguration>  {

	private TemplateConfiguration cfg;
	private final ServletContext context;
	

	public TemplateEngineSelector(ServletContext context) {
		this.context = context;
	}

	@Override
	public TemplateConfiguration getInstance() {

		if(this.cfg == null){
			
			String path = context.getRealPath("/WEB-INF/templates");
			
			if (isClassPresent("freemarker.template.Template")) {
				this.cfg = new FreemarkerConfiguration(path);
			}
			
			else if (isClassPresent("org.apache.velocity.Template")) {
				this.cfg = new VelocityConfiguration(path);
			}
			
			else
				throw new IllegalArgumentException("Could not find a template engine to use");
			
		}
		
		return cfg;
		
	}

	private boolean isClassPresent(String className) {
		try {
			Class.forName(className);
	        return true;
	     } catch (ClassNotFoundException e) {
	        return false;
	     }
	}

}
