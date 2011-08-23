package br.com.caelum.vraptor.templates.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;
import br.com.caelum.vraptor.templates.TemplatesConfiguration;
import br.com.caelum.vraptor.templates.plugins.freemarker.FreemarkerPlugin;
import br.com.caelum.vraptor.templates.plugins.velocity.VelocityPlugin;

@Component   
@ApplicationScoped
public class TemplatePluginSelector implements ComponentFactory<TemplatePlugin>  {
  
	private TemplatePlugin plugin;
	private final TemplatesConfiguration configs;
	private final Logger logger = LoggerFactory.getLogger(TemplatePluginSelector.class);

	public TemplatePluginSelector(TemplatesConfiguration configs) {
		this.configs = configs;
	}       
   
	@Override
	public TemplatePlugin getInstance() {      

		if(this.plugin == null){   
			
			if (isClassPresent("freemarker.template.Template")) {
				this.plugin = new FreemarkerPlugin(configs);
				logger.debug("Using Freemarker as Template Engine");
			}
			
			else if (isClassPresent("org.apache.velocity.Template")) {
				this.plugin = new VelocityPlugin(configs);
				logger.debug("Using Velocity as Template Engine");
			}
			 
			else if (isClassPresent("org.fusesource.scalate.Template")) {    
				this.plugin = new ScalatePlugin(configs);      
				logger.debug("Using Scalate as Template Engine");
			}  
			
			else
				throw new PluginNotFoundException("Could not find a template engine to use");
			
			logger.debug("Templates source path: " + configs.getTemplatesPath());
			
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
