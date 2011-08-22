package br.com.caelum.vraptor.templates.plugins.freemarker;


import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.templates.TemplateNotFoundException;
import br.com.caelum.vraptor.templates.TemplatesConfiguration;
import br.com.caelum.vraptor.templates.plugins.TemplatePlugin;
import br.com.caelum.vraptor.templates.plugins.TemplateRenderer;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MruCacheStorage;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerPlugin implements TemplatePlugin {
  
	private Configuration cfg;
	private final TemplatesConfiguration configs;
	private final Logger logger = LoggerFactory.getLogger(FreemarkerPlugin.class);
	
	public FreemarkerPlugin(TemplatesConfiguration configs){
		
		this.configs = configs;
		this.cfg = new Configuration();
		
		TemplateLoader loader;
		
		try {
			loader = new FileTemplateLoader(new File(configs.getTemplatesPath()));
			cfg.setTemplateLoader(loader);
			if(configs.allowCaching())
				cfg.setCacheStorage(new MruCacheStorage(0, Integer.MAX_VALUE));
			else
				cfg.setCacheStorage(new MruCacheStorage(0, 0));
		} catch (IOException e) {
			throw new RuntimeException("Template path could not be found");
		}
	}

	public Template getTemplate(String name){
		try {
			logger.debug("trying to load template " + name + "at" + configs.getTemplatesPath());
			return cfg.getTemplate(name + ".ftl");
		} catch (IOException e) {
			throw new TemplateNotFoundException("Could not find template " + name + " at " + configs.getTemplatesPath());
		}
	}

	@Override
	public TemplateRenderer getRenderer(String templateName){
		Template template = getTemplate(templateName);
		return new FreemarkerRenderer(template);
	}

}
