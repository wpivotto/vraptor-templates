package br.com.caelum.vraptor.templates;

import javax.servlet.ServletContext;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;
import br.com.caelum.vraptor.templates.freemarker.FreemarkerPlugin;
import br.com.caelum.vraptor.templates.velocity.VelocityPlugin;

@Component
@ApplicationScoped
public class TemplatePluginSelector implements ComponentFactory<TemplatePlugin>  {

	private TemplatePlugin plugin;
	private final ServletContext context;
	

	public TemplatePluginSelector(ServletContext context) {
		this.context = context;
	}

	@Override
	public TemplatePlugin getInstance() {

		if(this.plugin == null){
			
			String path = context.getRealPath("/WEB-INF/templates");
			
			if (isClassPresent("freemarker.template.Template")) {
				this.plugin = new FreemarkerPlugin(path);
			}
			
			else if (isClassPresent("org.apache.velocity.Template")) {
				this.plugin = new VelocityPlugin(path);
			}
			
			else
				throw new PluginNotFoundException("Could not find a template engine to use");
			
		}
		
		return plugin;
		
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
