package br.com.caelum.vraptor.templates.plugins;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;
import br.com.caelum.vraptor.templates.PluginNotFoundException;

@Component
@ApplicationScoped
public class TemplatePluginSelector implements ComponentFactory<TemplatePlugin>  {

	private TemplatePlugin plugin;
	private final ServletContext context;
	private final Logger logger = LoggerFactory.getLogger(TemplatePluginSelector.class);

	public TemplatePluginSelector(ServletContext context) {
		this.context = context;
	}

	@Override
	public TemplatePlugin getInstance() {

		if(this.plugin == null){
			
			String path = context.getRealPath("/WEB-INF/templates");
			
			if (isClassPresent("freemarker.template.Template")) {
				this.plugin = new FreemarkerPlugin(path);
				logger.debug("Using Freemarker as Template Engine");
			}
			
			else if (isClassPresent("org.apache.velocity.Template")) {
				this.plugin = new VelocityPlugin(path);
				logger.debug("Using Velocity as Template Engine");
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
