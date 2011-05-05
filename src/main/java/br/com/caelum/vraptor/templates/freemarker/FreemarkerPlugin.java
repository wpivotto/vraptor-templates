package br.com.caelum.vraptor.templates.freemarker;


import java.io.File;
import java.io.IOException;

import br.com.caelum.vraptor.templates.TemplatePlugin;
import br.com.caelum.vraptor.templates.TemplateRenderer;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerPlugin implements TemplatePlugin {
  
	private Configuration cfg;
	
	public FreemarkerPlugin(String path){
		
		cfg = new Configuration();
		TemplateLoader loader;
		
		try {
			loader = new FileTemplateLoader(new File(path));
			cfg.setTemplateLoader(loader);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Template getTemplate(String name) throws IOException {
		return cfg.getTemplate(name + ".ftl");
	}

	@Override
	public TemplateRenderer getRenderer(String templateName) throws IOException {
		Template template = getTemplate(templateName);
		return new FreemarkerRenderer(template);
	}

}
