package br.com.caelum.vraptor.templates;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;
import br.com.caelum.vraptor.templates.freemarker.FreemarkerConfiguration;
import br.com.caelum.vraptor.templates.velocity.VelocityConfiguration;

@Component
@ApplicationScoped
public class TemplateEngineSelector implements ComponentFactory<TemplateConfiguration>  {

	private TemplateConfiguration cfg;
	
	@Override
	public TemplateConfiguration getInstance() {

		if(this.cfg == null){
			
			if (isClassPresent("freemarker.template.Template")) {
				this.cfg = new FreemarkerConfiguration();
			}
			
			else if (isClassPresent("org.apache.velocity.Template")) {
				this.cfg = new VelocityConfiguration();
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
