package br.com.caelum.vraptor.templates.freemarker;


import java.io.IOException;

import br.com.caelum.vraptor.templates.TemplateConfiguration;
import br.com.caelum.vraptor.templates.TemplateRenderer;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerConfiguration implements TemplateConfiguration {
  
	private Configuration cfg;

	public FreemarkerConfiguration(){
		cfg = new Configuration();
		ClassTemplateLoader loader = new ClassTemplateLoader(getClass(), "/templates");
		cfg.setTemplateLoader(loader);
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
