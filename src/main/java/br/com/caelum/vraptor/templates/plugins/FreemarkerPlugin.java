package br.com.caelum.vraptor.templates.plugins;


import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.templates.TemplateNotFoundException;
import br.com.caelum.vraptor.templates.TemplateRenderer;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerPlugin implements TemplatePlugin {
  
	private Configuration cfg;
	private final String path;
	private final Logger logger = LoggerFactory.getLogger(FreemarkerPlugin.class);
	
	public FreemarkerPlugin(String path){
		
		this.path = path;
		this.cfg = new Configuration();
		
		TemplateLoader loader;
		
		try {
			loader = new FileTemplateLoader(new File(path));
			cfg.setTemplateLoader(loader);
		} catch (IOException e) {
			throw new RuntimeException("Template path could not be found");
		}
	}

	public Template getTemplate(String name){
		try {
			logger.debug("trying to load template " + name + "at" + path);
			return cfg.getTemplate(name + ".ftl");
		} catch (IOException e) {
			throw new TemplateNotFoundException("Could not find template " + name + " at " + path);
		}
	}

	@Override
	public TemplateRenderer getRenderer(String templateName){
		Template template = getTemplate(templateName);
		return new FreemarkerRenderer(template);
	}

}
